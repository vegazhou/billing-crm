package com.kt.service.billing;

import com.kt.biz.bean.KeyValueBean;
import com.kt.common.Constants;
import com.kt.common.util.BeanHelper;
import com.kt.common.util.CommonUtil;
import com.kt.entity.mysql.billing.TelephoneCallinNumberEntity;
import com.kt.entity.mysql.billing.TelephoneLandKindEntity;
import com.kt.entity.mysql.billing.TelephoneTollMappingEntity;
import com.kt.entity.mysql.crm.ChargeSchemeAttribute;
import com.kt.entity.mysql.crm.Customer;
import com.kt.entity.mysql.crm.Rate;
import com.kt.repo.edr.EdrRepository;
import com.kt.repo.edr.bean.TelephoneBillDetail;
import com.kt.repo.edr.bean.WbxSite;
import com.kt.repo.mysql.billing.TelephoneCallinNumberRepository;
import com.kt.repo.mysql.billing.TelephoneCommonRepository;
import com.kt.repo.mysql.billing.TelephoneLandKindRepository;
import com.kt.repo.mysql.billing.TelephoneTollMappingRepository;
import com.kt.util.FileUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2016/9/7.
 */
@Service
public class TelephoneService {
    private static final Log logger = LogFactory.getLog(TelephoneService.class);

    @Autowired
    private EdrRepository edrRepository;

    @Autowired
    private TelephoneCommonRepository telephoneCommonRepository;

//    @Autowired
//    private TelephoneCallinNumberRepository telephoneCallinNumberRepository;

    @Autowired
    private TelephoneLandKindRepository telephoneLandKindRepository;

    @Autowired
    private TelephoneTollMappingRepository telephoneTollMappingRepository;

    public List<TelephoneBillDetail> getCustomerTelephoneBillDetail(String customerId,  Date startDate, Date endDate) throws Exception{
        List<TelephoneBillDetail> details = new ArrayList<TelephoneBillDetail>();
        List<String> siteNames = telephoneCommonRepository.getSiteNamesByCustomerId(customerId);
        for(String s: siteNames){
            WbxSite site = edrRepository.getWbxSiteBySiteName(s);
            if(site == null){
                logger.error("site doesn't exist! siteName:" + s);
                throw new Exception("site doesn't exist! siteName:" + s);
            }
            try{
                List<TelephoneBillDetail> siteDetails = getTelephoneBillDetail(site.getSiteId(), startDate, endDate);
                details.addAll(siteDetails);
            }catch(Exception e){
                logger.error("generate bill fail!", e);
                logger.error("generate bill fail for site " + site.getSiteName() + ", siteId " + site.getSiteId());
                throw new Exception("Generate bill fail!", e);
            }
        }

        return details;
    }

    public List<TelephoneBillDetail> getTelephoneBillDetail(int siteId, Date startDate, Date endDate) throws Exception{

        List<TelephoneTollMappingEntity> telephoneTollMappingEntities = telephoneTollMappingRepository.getTelephoneTollMapping(startDate, endDate);

        Map<String, String> phoneMapping = new HashMap<String, String>();
        for(TelephoneTollMappingEntity entity: telephoneTollMappingEntities){
            phoneMapping.put(entity.getRealNumber().trim(), entity.getCode());
        }

        Set<Integer> siteIds = new HashSet<Integer>();
        siteIds.add(siteId);
        List<TelephoneBillDetail> details = edrRepository.getTelephoneBillDetail(siteIds, startDate, endDate);
        WbxSite site = edrRepository.getWbxSiteBySiteId(siteId);
        Customer customer = telephoneCommonRepository.getCustomerBySiteName(site.getSiteName());
        List<ChargeSchemeAttribute> pstnOrderAttribute = telephoneCommonRepository.getStandardPSTNRateAttribute(customer.getPid(), startDate, endDate);
        List<Rate> rates = null;

        String schemeEntityId = null;
        Map<String, Rate> rateMap = new HashMap<String, Rate>();
        Map<String, Rate> landKindRates = new HashMap<String,Rate>();
        for (ChargeSchemeAttribute beanSite : pstnOrderAttribute) {
            if ("COMMON_SITES".equals(beanSite.getName()) && site.getSiteName().equals(beanSite.getValue())) {
                schemeEntityId = beanSite.getEntityId();
                logger.debug("Site exists!");
                for (ChargeSchemeAttribute beanPstn : pstnOrderAttribute) {
                    if ("PSTN_RATES_ID".equals(beanPstn.getName()) && schemeEntityId.equals(beanPstn.getEntityId())) {
                        logger.debug("Rates exists!");
                        rates = telephoneCommonRepository.getPSTNDetailRates(beanPstn.getValue());
                        for (Rate rate : rates) {
                            rateMap.put(rate.getCode(), rate);
                        }
                        break;
                    }
                }
                break;
            }
        }

//        if(schemeEntityId == null){
//            logger.error("SchemeEntityId is null!");
//            throw new Exception("The site may be unavailable! site name is " + site.getSiteName());
//        }
//
//        if(rates == null){
//            logger.error("Rates is null!");
//            throw new Exception("The rate is null.");
//        }



        List<TelephoneLandKindEntity> landKinds = telephoneLandKindRepository.getTelephoneLandKind(startDate, endDate);

        for (TelephoneLandKindEntity entity : landKinds) {
            String countryCode = entity.getCountryCode();
            String areaCode = entity.getAreaCode();
            Rate rate = rateMap.get("10" + (Integer.parseInt(entity.getLandKind()) - 1));
            if (StringUtils.isNotBlank(areaCode)) {
                if (areaCode.indexOf(",") <= 0) {
                    String[] areas = areaCode.split(",");
                    for (String s : areas) {
                        landKindRates.put(countryCode + "||" + s, rate);
                    }
                } else {
                    landKindRates.put(countryCode + "||" + areaCode, rate);
                }
            } else {
                landKindRates.put(countryCode, rate);
            }
        }


        for(TelephoneBillDetail detail: details){
            try {
                detail.setConfName(new String(detail.getConfName().getBytes("ISO-8859-1"), "UTF-8"));
                detail.setHostName(new String(detail.getHostName().getBytes("ISO-8859-1"), "UTF-8"));
                Rate mappingRate = null;

                boolean isPhoneCall = false;
                if(detail.getSessionType().equals("CALLIN")){
                    String code = phoneMapping.get(detail.getAccessNumber());
                    if(code == null){
                        logger.debug(CommonUtil.getJsonString(detail));
                        logger.debug("Access number is " + detail.getAccessNumber() + ". code is " + code);
                    }
                    if(code == null){
                        if("Domestic".equals(detail.getCallType())){
                            code = "6"; //  code is 1 if 400 tel;
                        }else{
                            logger.error("The code is null, Access number is " +  detail.getAccessNumber());
                            logger.error(ToStringBuilder.reflectionToString(detail));
                            throw new Exception("The code is null, Access number is " +  detail.getAccessNumber());
                        }
                    }
                    detail.setRateCode(code);
                    mappingRate = rateMap.get(code);
                    isPhoneCall = true;
                }else if(detail.getSessionType().equals("CALLOUT")){
                    if(Constants.EDR_CALL_TYPE_INTERNATIONAL.equals(detail.getCallType())){
                        String countryCode = detail.getDestCountryCode();
                        String areaCode = detail.getDestAreaCode();
                        mappingRate = landKindRates.get(countryCode + "||" + areaCode);
                        if(mappingRate == null){
                            mappingRate = landKindRates.get(countryCode);
                        }
                    }else if(Constants.EDR_CALL_TYPE_DOMESTIC.equals(detail.getCallType() ) && "86".equals(detail.getDestCountryCode()) ){ //StringUtils.isBlank(detail.getCallType()) && "86".equals(detail.getDestCountryCode())){ //"Domestic".equals(detail.getCallType())
                        String code = "7";
                        mappingRate = rateMap.get(code);
                        detail.setRateCode(code);
                    }else{
                        logger.error("Call type is wrong. CalledNum:" + detail.getCalledNum());
                        logger.error(CommonUtil.getJsonString(detail));
                        throw new Exception("Call tpye is wrong");
                    }
                    isPhoneCall = true;
                }
                if(isPhoneCall){
                    if(mappingRate == null){
                        logger.error("Mapping Rate is null!" + "; schemeEntityId:" + schemeEntityId + "; rate:" + rates);
                        logger.error(ToStringBuilder.reflectionToString(detail));
                        throw new Exception("No valid rate exists.");
                    }else{
                        detail.setRate(new BigDecimal( (int)((mappingRate.getRate() + mappingRate.getServiceRate())*100)).divide(new BigDecimal(100)));
                    }
//                    logger.debug(">>>>>>>>>>>>>>>>>>> Code:" + ToStringBuilder.reflectionToString(mappingRate) + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    detail.setFee(new BigDecimal(detail.getTotalDuration()).multiply( new BigDecimal( (int)((mappingRate.getRate() + mappingRate.getServiceRate())*100)).divide(new BigDecimal(100))));
                }

            } catch (UnsupportedEncodingException e) {
                logger.error(e);
                throw new Exception("UnsupportedEncodingException", e);
            }
        }

        return details;
    }

    public void exportExcel(String title, String filePath, List<TelephoneBillDetail> telephoneBillDetails){

        Class<TelephoneBillDetail> c = TelephoneBillDetail.class;
        List<Object[]> fieldList = new ArrayList<Object[]>();

        fieldList.add(new Object[]{BeanHelper.getGetMethod("confName", c), "confName"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("confId", c), "confId"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("meetingNumber", c), "meetingNumber"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("hostName", c), "hostName"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("hostEmail", c), "hostEmail"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("startTime", c), "startTime"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("endTime", c), "endTime"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("totalDuration", c), "totalDuration"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("rate", c), "rate"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("fee", c), "fee"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("callingNum", c), "callingNum"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("calledNum", c), "calledNum"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("sessionType", c), "sessionType"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("callType", c), "callType"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("rateCode", c), "rateCode"});

        fieldList.add(new Object[]{BeanHelper.getGetMethod("privateTalkStartTime", c), "privateTalkStartTime"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("privateTalkEndTime", c), "privateTalkEndTime"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("duration", c), "duration"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("privateDuration", c), "privateDuration"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("origCountryCode", c), "origCountryCode"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("origAreaCode", c), "origAreaCode"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("destCountryCode", c), "destCountryCode"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("destAreaCode", c), "destAreaCode"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("accessNumber", c), "accessNumber"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("siteName", c), "siteName"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("meetingStartTime", c), "DataMeeting StartTime"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("meetingEndTime", c), "DataMeeting EndTime"});

        try {
            FileUtil.exportExcel(filePath,title, fieldList, telephoneBillDetails, true);
        } catch (Exception e) {
            logger.error("export excel file error:", e);
        }
    }
}
