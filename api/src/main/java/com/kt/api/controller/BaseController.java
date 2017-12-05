package com.kt.api.controller;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import com.kt.api.controller.datatable.DataTableVo;
import com.kt.exception.ExceptionKeys;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kt.api.common.APIConstants;
import com.kt.api.common.APIUtil;
import com.kt.common.RedisConstants;
import com.kt.common.exception.ApiException;
import com.kt.common.exception.ExceptionKey;
import com.kt.service.CacheService;
import com.fasterxml.jackson.databind.ObjectMapper;



/**
 * BaseController
 */
public class BaseController {

	public static final String ORDER_DESC = "desc";
	public static final String ORDER_ASC = "asc";

	public static final String FAIL = "FAIL";

	@Autowired
	private MessageSource msgSource;

	@Autowired
	private CacheService cacheService;

	/**
	 * handleApiException
	 *
	 * @param ex
	 *            ApiException
	 * @return ResponseEntity<String>
	 */
	@ExceptionHandler(ApiException.class)
	protected ResponseEntity<String> handleApiException(ApiException ex) {
		String msg = msgSource.getMessage(ex.getErrorKey(), ex.getValues(), null);
		return generateResponse(ex.getErrorKey(), msg);
	}

	/**
	 * handelMethodArgumentNotValidException
	 *
	 * @param ex
	 *            MethodArgumentNotValidException
	 * @return ResponseEntity<String>
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<String> handelMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		List<ObjectError> errors = ex.getBindingResult().getAllErrors();
		String key;
		if (errors == null || errors.isEmpty()) {
			key = "global.ServiceInternalError";
		} else {
			key = errors.get(0).getDefaultMessage();
		}
		String msg = msgSource.getMessage(key, null, null);
		return generateResponse(key, msg);
	}

	/**
	 * getUserMapByToken
	 *
	 * @param token
	 *            String
	 * @return Map
	 */
	protected Map<Object, Object> getUserMapByToken(String token) {
		Map<Object, Object> map = null;
		if (StringUtils.isNotBlank(token)) {
			map = cacheService.getUserMapByToken(token);
		}
		return map;
	}

	/**
	 * getUserIdByToken
	 *
	 * @param token
	 *            String
	 * @return String
	 */
	protected String getUserIdByToken(String token) {
		String userId = "1212";
		/*
		if (StringUtils.isNotBlank(token)) {
			userId = cacheService.getPropertyValueByToken(token, RedisConstants.REDIS_NAME_USERID);
		}*/
		return userId;
	}
	
	
	

	/**
	 * getOrgIdByToken
	 *
	 * @param token
	 *            String
	 * @return String
	 */
	protected String getOrgIdByToken(String token) {
		String orgId = "12223";
		/*
		if (StringUtils.isNotBlank(token)) {
			orgId = cacheService.getPropertyValueByToken(token, RedisConstants.REDIS_NAME_ORGID);
		}*/
		return orgId;
	}

	private ResponseEntity<String> generateResponse(String key, String msg) {
		String[] msgs = msg.split(";");
		if (msgs.length < 2) {
			msgs = new String[] { "400", "Unknow error!" };
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("key", key);
		jsonObject.put("message", msgs[1]);
		JSONObject error = new JSONObject();
		error.put("error", jsonObject);
		return new ResponseEntity<String>(error.toString(), HttpStatus.valueOf(APIUtil.str2Int(msgs[0], 400)));
	}

	private boolean hasPermission(String resourceName, String operationMethod, JSONArray permissionArray) {
		JSONObject permissionJson;
		boolean hasPermission = false;
		String resourceStr;
		for (int i = 0; i < permissionArray.size(); i++) {
			permissionJson = permissionArray.getJSONObject(i);
			resourceStr = permissionJson.getString("resourceName") + permissionJson.getString("operationMethod");
			if ((resourceName + operationMethod).equals(resourceStr)) {
				hasPermission = true;
			}

		}
		return hasPermission;
	}

	/**
	 * checkPermission
	 *
	 * @param resourceName
	 *            String
	 * @param operationMethod
	 *            String
	 * @param token
	 *            String
	 */
	protected void checkPermission(String resourceName, String operationMethod, String token) {
		String strPermissionArray = cacheService.getPropertyValueByToken(token,
				RedisConstants.REDIS_NAME_PERMISSIONLIST);
		JSONArray permissionArray = JSONArray.fromObject(strPermissionArray);
		if (strPermissionArray == null) {
			throw new ApiException(ExceptionKey.AUTHOR_PERMISSIONLISTNULL_ERROR);
		}
		boolean hasPermission = hasPermission(resourceName, operationMethod, permissionArray);
		if (!hasPermission) {
			throw new ApiException(ExceptionKey.AUTHOR_NOPERMISSION_ERROR);
		}
	}

	/**
	 * verifyToken
	 *
	 * @param token
	 *            String
	 */
	protected void verifyToken(String token) {
		String userId = getUserIdByToken(token);
		if (StringUtils.isBlank(userId)) {
			throw new ApiException(ExceptionKeys.INVALID_TOKEN);
		} else {
			updateUserCache(token, userId);
		}
	}

	private void updateUserCache(String token, String userId) {
		if (cacheService.getExpireTime(RedisConstants.REDIS_NAME_TOKEN + token, TimeUnit.MILLISECONDS) < 60 * 1000) {
			cacheService.setExpireTime(RedisConstants.REDIS_NAME_TOKEN + token, 2, TimeUnit.HOURS);
			cacheService.setExpireTime(RedisConstants.REDIS_NAME_UIDS_TOKEN + userId, 2, TimeUnit.HOURS);
			cacheService.setExpireTime(RedisConstants.REDIS_NAME_UIDS_PROPERTY + userId, 2, TimeUnit.HOURS);
		}
	}

	/**
	 * getEventStr
	 *
	 * @param action
	 *            int
	 * @param target
	 *            String
	 * @param errorKey
	 *            String
	 * @param request
	 *            HttpServletRequest
	 * @return String
	 */
	protected String getEventStr(int action, String target, String errorKey, HttpServletRequest request) {
		String result = "";
		result += "\"" + APIUtil.getUUID() + "\",";
		result += "\"" + action + "\",";
		if (StringUtils.isBlank(errorKey)) {
			result += "\"1\",";
		} else {
			result += "\"0\",";
		}
		result += "\"" + target + "\",";
		result += "\"" + request.getRequestURI() + "\",";
		result += "\"" + errorKey + "\",";
		result += "\"" + request.getHeader(APIConstants.HEADER_NAME_USERAGENT) + "\",";
		result += "\"" + getClientIp(request) + "\",";
		result += "\"" + APIUtil.now() + "\",";
		result += "\"" + request.getHeader(APIConstants.HEADER_NAME_SOURCE) + "\",";
		result += "\"" + getUserIdByToken(request.getHeader(APIConstants.HEADER_NAME_TOKEN)) + "\"";
		return result;
	}

	private String getClientIp(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (StringUtils.isBlank(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}
	
	/**
	 * Pase parameter for datatable from request
	 * @param request
	 * @return
	 */
	protected DataTableVo parseData4DT( HttpServletRequest request ){
		DataTableVo dt = new DataTableVo() ;
		try{
			String sEcho = request.getParameter("sEcho") ;
			String start = request.getParameter("iDisplayStart") ;
			int iDisplayStart = StringUtils.isEmpty(start) ? 0 :
				Integer.parseInt( start ) ;
			
			String length = request.getParameter("iDisplayLength") ;
			int iDisplayLength = StringUtils.isEmpty(length) ? 10 :
				Integer.parseInt( length ) ;
			
			int sortColId = StringUtils.isEmpty(request.getParameter("iSortCol_0"))?0:
				Integer.parseInt(request.getParameter("iSortCol_0")) ;
			
			String sortColName = request.getParameter("mDataProp_"+sortColId) ;
			String sortOrder = request.getParameter("sSortDir_0") ;
			
			dt.setsEcho(sEcho);
			dt.setiDisplayLength(iDisplayLength);
			dt.setiDisplayStart(iDisplayStart);
			dt.setSortField(sortColName);
			dt.setSortType(sortOrder);
		}catch(Exception e){
			e.printStackTrace();
		}
		return dt ;
	}
	

	
	
	/**
	 * Format data to json of datatable 
	 * @param dt
	 * @return
	 */
	protected String formateData2DT( Object dt ){
		if( dt == null ){
			return null ;
		}
		StringWriter writer = new StringWriter() ;
		ObjectMapper mapper = new ObjectMapper() ;
		try{
			mapper.writeValue( writer , dt ) ;
			writer.flush();
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return writer.toString();
	}
}
