package com.kt.test.billing;

import com.kt.common.Constants;
import com.kt.common.util.BeanHelper;
import com.kt.entity.mysql.crm.Customer;
import com.kt.exception.WafException;
import com.kt.repo.edr.bean.BillDetail;
import com.kt.repo.edr.bean.TelephoneBillDetail;
import com.kt.repo.edr.bean.WbxSite;
import com.kt.service.CustomerService;
import com.kt.service.EdrService;
import com.kt.service.billing.TelephoneService;
import com.kt.util.DateUtils;
import com.kt.util.FileUtil;
import com.kt.util.SortUtil;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestTelephoneService {

    private static final Log logger = LogFactory.getLog(TestTelephoneService.class);

    @Configuration
    @ComponentScan(basePackages = {"com.kt"})
    static class ContextConfiguration {
    }

    @Autowired
    TelephoneService telephoneService;

    @Autowired
    CustomerService customerService;

    @Autowired
    EdrService edrService;

    @Test
    public void testCustomerBillDetail(){
        Date now = new Date();

        String code = "SM40115HD3";//"SM40089HD4";//"SM40115HD3";
        Customer customer = customerService.findFirstCustomerByCode(code);
        String customerId = customer.getPid();

        Date[] dates = DateUtils.getStartEndDateByBillPeriod(201610);
        String billPeriod = DateUtils.format("yyyy-MM", dates[0]);
        BigDecimal internationDisCount = BigDecimal.valueOf(1);
        try{
            List<TelephoneBillDetail> details = telephoneService.getCustomerTelephoneBillDetail(customerId, dates[0], dates[1]);
            SortUtil.anyProperSort(details, "confId", true);
            BigDecimal internationalCallOut = new BigDecimal(0);
            BigDecimal internationalCallin = new BigDecimal(0);
            BigDecimal domesticCall = new BigDecimal(0);
            int domesticCallMinutes = 0;
            for(TelephoneBillDetail detail: details){
                if(Constants.EDR_CALL_TYPE_INTERNATIONAL.equals(detail.getCallType())){
                    detail.setFee(detail.getFee().multiply(internationDisCount));
                }
                logger.debug(ToStringBuilder.reflectionToString(detail));
                if("CALLOUT".equals(detail.getSessionType()) || "CALLIN".equals(detail.getSessionType())) {
                    if ("International".equals(detail.getCallType())) {
                        if ("CALLOUT".equals(detail.getSessionType())) {
                            internationalCallOut = internationalCallOut.add(detail.getFee());
                        } else {
                            internationalCallin = internationalCallin.add(detail.getFee());
                        }
                    } else {
                        domesticCall = domesticCall.add(detail.getFee());
                        domesticCallMinutes += detail.getDuration() + detail.getPrivateDuration();
                    }
                }
            }

            logger.debug("Customer Id:" + customerId + "  International CALLOUT:" + internationalCallOut);
            logger.debug("Customer Id:" + customerId + "  International CALLIN:" + internationalCallin);
            logger.debug("Customer Id:" + customerId + "  Domestic CALL:" + domesticCall);
            logger.debug("Customer Id:" + customerId + "  Domestic CALL Minutes:" + domesticCallMinutes);
            logger.debug("Cost " + (new Date().getTime() - now.getTime())/1000 + " second(s)!");
            String title = "电话费用清单"  + billPeriod + " 【 电话总收入：" + ( internationalCallOut.add(internationalCallin).add(domesticCall) ) + "元, 国际呼出：" + internationalCallOut + "元, 国际呼入："
                    + internationalCallin + "元, 国内呼入呼出：" + domesticCall + "元, 国内呼入呼出分钟数：" + domesticCallMinutes + "分钟 】";
            String filePath = "/" + code  + "_"+billPeriod + ".xls";
            telephoneService.exportExcel(title, filePath, details);
            exportMeetingSummaryFee(code, details);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void exportMeetingSummaryFee(String code, List<TelephoneBillDetail> details){

        List<TelephoneBillDetail> newDetails =  new ArrayList<TelephoneBillDetail>();
        long confId= 0;
        TelephoneBillDetail currentDetail = null;
        for(TelephoneBillDetail detail: details){
            if(detail.getConfId() == confId){
                if(detail.getFee() != null ){
                    currentDetail.setFee(currentDetail.getFee().add(detail.getFee()));
                }
            }else{

                currentDetail = detail;
                if(currentDetail.getFee() == null ){
                    currentDetail.setFee(new BigDecimal(0));
                }

                confId = currentDetail.getConfId();
                newDetails.add(currentDetail);
            }

        }

        if(newDetails == null || newDetails.size() == 0){
            logger.error("No data!");
            return;
        }
        String billPeriod = DateUtils.format("yyyy-MM", newDetails.get(0).getMeetingStartTime());
        String filePath = "/" + code  + "_"+ billPeriod + "_meetingfee.xls";
        String title = "会议费用列表"  + billPeriod;
        Class<TelephoneBillDetail> c = TelephoneBillDetail.class;
        List<Object[]> fieldList = new ArrayList<Object[]>();

        fieldList.add(new Object[]{BeanHelper.getGetMethod("confName", c), "confName"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("meetingNumber", c), "meetingNumber"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("confId", c), "confId"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("hostName", c), "hostName"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("hostEmail", c), "hostEmail"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("meetingStartTime", c), "meetingStartTime"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("meetingEndTime", c), "meetingEndTime"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("fee", c), "fee"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("siteName", c), "siteName"});


        try {
            FileUtil.exportExcel(filePath,title, fieldList, newDetails, false);
        } catch (Exception e) {
            logger.error("export excel file error:", e);
        }
    }

//    @Test
//    public void testGetTelephoneBillDetail(){
//
//        String sitename = "ecmc2520160701001";
//        WbxSite site  = edrService.getWbxSiteBySiteName(sitename);
//        int siteId = site.getSiteId();//20997
//        Date startDate = DateUtils.parseDate("2016-07-04 00:00:00");
//        Date endDate = DateUtils.parseDate("2016-10-01 00:00:00");
//        try{
//            List<TelephoneBillDetail> details = telephoneService.getTelephoneBillDetail(siteId, startDate, endDate);
//            BigDecimal internationalCallOut = new BigDecimal(0);
//            BigDecimal internationalCallin = new BigDecimal(0);
//            BigDecimal domesticCall = new BigDecimal(0);
//            int domesticCallMinutes = 0;
//
//            List<TelephoneBillDetail> newDetails =  new ArrayList<TelephoneBillDetail>();
//            for(TelephoneBillDetail detail: details){
//
//                if(true /*detail.getHostEmail().equals("baijie@goldmantis.com") */){
//                    logger.debug(ToStringBuilder.reflectionToString(detail));
//                    if ("CALLOUT".equals(detail.getSessionType()) || "CALLIN".equals(detail.getSessionType())) {
//                        if ("International".equals(detail.getCallType())) {
//                            if ("CALLOUT".equals(detail.getSessionType())) {
//                                internationalCallOut = internationalCallOut.add(detail.getFee());
//                            } else {
//                                internationalCallin = internationalCallin.add(detail.getFee());
//                            }
//                        } else {
//                            domesticCall = domesticCall.add(detail.getFee());
//                            domesticCallMinutes += detail.getDuration();
//                        }
//                    }
//                    newDetails.add(detail);
//                }
//            }
//
//            logger.debug("SiteId:" + siteId + "  International CALLOUT:" + internationalCallOut);
//            logger.debug("SiteId:" + siteId + "  International CALLIN:" + internationalCallin);
//            logger.debug("SiteId:" + siteId + "  Domestic CALL:" + domesticCall);
//            logger.debug("SiteId:" + siteId + "  Domestic CALL Minutes:" + domesticCallMinutes);
//            String title = "电话费用清单"  + DateUtils.format("yyyy-MM", startDate);
//            String filePath = "/" + sitename  + "_"+DateUtils.format("yyyy-MM", startDate) + ".xls";
//            telephoneService.exportExcel(title, filePath, newDetails);
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//    }
}
