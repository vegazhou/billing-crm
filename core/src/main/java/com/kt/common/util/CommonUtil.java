package com.kt.common.util;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import com.kt.common.Constants;
import com.kt.common.StringBean;


public class CommonUtil {

    /**
     * Generate UUID for P_ID
     *
     * @return String
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generate xiaoxin number
     *
     * @return String
     */
    public static String getXiaoXinCode() {
        int randomCode = RandomUtils.nextInt(99999999);
        if (randomCode < 100000) {
            randomCode += 100000;
        }
        return randomCode + "";
    }
    
	/**
	 * @param provinceid
	 * @param cityid
	 * @param areaid
	 * @return
	 */
	public static String getOrgRegionType(String provinceid,String cityid,String areaid){
		if(StringUtils.isBlank(areaid) && StringUtils.isBlank(cityid)){
			return Constants.ORG_REGION_PROVINCE;
		}else if(StringUtils.isBlank(areaid)){
			return Constants.ORG_REGION_CITY;
		}else{
			return Constants.ORG_REGION_AREA;
		}
	}
	
    /**
     * Generate random password
     *
     * @return String
     */
    public static String getRandomInitPassword() {
        int randomCode = RandomUtils.nextInt(999999);
        if (randomCode < 100000) {
            randomCode += 100000;
        }
        return randomCode + "";
    }
    
	public static boolean hasViewPrivilege(int userType) {
		return Constants.ORG_USER_TYPE_ADMIN == userType || Constants.ORG_USER_TYPE_SCHOOL_ADMIN == userType
				|| Constants.ORG_USER_TYPE_CLASS_ADMIN == userType;
	}
	
	public static boolean getViewFlag(int userType, String currentUserID, String creator, List<String> agentList) {
		if (Constants.ORG_USER_TYPE_SALESMAN == userType) {
			return false;
		}
		boolean viewFlag = currentUserID.equals(creator);
		if (!viewFlag && Constants.ORG_USER_TYPE_AGENT == userType) {
			viewFlag = agentList.contains(creator);
		}
		return viewFlag;
	}


	
	public static String covertList2String(List<StringBean> beanList) {
		StringBuffer result = new StringBuffer();
		if (beanList != null) {
			for (int i = 0; i < beanList.size(); i++) {
				result.append("'").append(beanList.get(i).getParamName()).append("'");
				if (i != beanList.size() - 1) {
					result.append(", ");
				}
			}
		}
		return result.toString();
	}
	
	/**  
	* 防止sql注入  
	*  
	* @param sql  
	* @return  
	*/ 
	public static String transactSQLInjection(String sql) {
		if(sql == null)return null;
		return sql.replaceAll(".*([';]+|(--)+).*", " ");
	}

	public static String getJsonString(Object object){
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String json = ow.writeValueAsString(object);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
