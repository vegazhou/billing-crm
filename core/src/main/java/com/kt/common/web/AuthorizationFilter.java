package com.kt.common.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * HttpServletRequestReplacedFilter.
 */
public class AuthorizationFilter implements Filter {

	/** The string redis template. */
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	

	/** The Constant HEADER_NAME_UID. */
	public static final String HEADER_NAME_UID = "Uid";

	/** The Constant HEADER_NAME_TOKEN. */
	public static final String HEADER_NAME_TOKEN = "AccessToken";

	/**
	 * destroy.
	 */
	@Override
	public void destroy() {
		// Do nothing
	}

	/**
	 * doFilter.
	 *
	 * @param request
	 *            ServletRequest
	 * @param response
	 *            ServletResponse
	 * @param chain
	 *            FilterChain
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ServletException
	 *             the servlet exception
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String accessToken = req.getHeader(HEADER_NAME_TOKEN);
		String uid = req.getHeader(HEADER_NAME_UID);
		String redisUserId = (String) stringRedisTemplate.opsForHash().get("tokens:" + accessToken, "userId");
		String permissionArray = (String) stringRedisTemplate.opsForHash().get("tokens:" + accessToken, "permissionlist");
		
		
		if (redisUserId == null || !redisUserId.equals(uid)) {
			res.setStatus(HttpStatus.SC_FORBIDDEN);
			res.setHeader("content-type", "text/html;charset=UTF-8");
			OutputStream outputStream = res.getOutputStream();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("key", "global.token.invalid");
			jsonObject.put("message", "token is invalid");
			JSONObject error = new JSONObject();
			error.put("error", jsonObject);
			outputStream.write(error.toString().getBytes());
		} else {
			if (stringRedisTemplate.getExpire("tokens:" + accessToken, TimeUnit.MILLISECONDS) < 60 * 1000) {
				stringRedisTemplate.expire("tokens:" + accessToken, 2, TimeUnit.HOURS);
			}
		}
	
		chain.doFilter(request, response);
		
	}



	/**
	 * init.
	 *
	 * @param arg0
	 *            FilterConfig
	 * @throws ServletException
	 *             the servlet exception
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, arg0.getServletContext());

	}
}