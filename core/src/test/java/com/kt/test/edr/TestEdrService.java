package com.kt.test.edr;

import com.kt.biz.bean.KeyValueBean;
import com.kt.biz.bean.PortsUsageBean;
import com.kt.common.util.CommonUtil;
import com.kt.entity.mysql.billing.PortsOverflowDetail;
import com.kt.entity.mysql.crm.WebExSite;
import com.kt.repo.edr.bean.WbxSite;
import com.kt.service.EdrService;
import com.kt.service.WebExSiteService;
import com.kt.util.DateUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Created by jianf on 2016/7/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestEdrService {
    private static final Log logger = LogFactory.getLog(TestEdrService.class);

    @Configuration
    @ComponentScan(basePackages = {"com.kt"})
    static class ContextConfiguration {
    }

    @Autowired
    EdrService edrService;

    @Autowired
    WebExSiteService webExSiteService;
//    @Test
//    public void getSitesPorts() throws WafException {
//        Set<Integer> set = new HashSet<Integer>();
//        set.add(22122); //kmeritsz
//        set.add(18827); // pland
//        set.add(21122); // ppg
//        set.add(21617);
//        edrService.getSitesPorts(set, 2016, 7);
//    }

    /*
    @Test
    public void getBillDetail() throws WafException {
        logger.debug("getBillDetail");

        Set<Integer> set = new HashSet<Integer>();
//        set.add(22122); //kmeritsz
//        set.add(18827); // pland
        int siteId = 22122;
        String siteName = "kmeritsz";
        set.add(siteId);
//        set.add(20997); // moma1
//        set.add(21002); // moma2
        List<BillDetail> details = edrService.getBillDetail(set, 2016, 6);

//        String title = "费用详单";
        Class<BillDetail> c = BillDetail.class;
        List<Object[]> fieldList = new ArrayList<Object[]>();
        fieldList.add(new Object[]{BeanHelper.getGetMethod("hostName", c), "主持人"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("confId", c), "会议号"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("confName", c), "会议主题"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("startTime", c), "开始时间"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("endTime", c), "结束时间"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("callingNum", c), "参会人号码"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("calledNum", c), "会议平台接入号码"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("callType", c), "接入类型"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("duration", c), "与会时长"});

        try {
//            FileUtil.exportExcel( "f:/memo.xls", fieldList, details);
            String charset = "GBK";
            FileUtil.exportCSV("f:/BillDetail-" + siteName + "-" + charset + "-" + siteId+ ".csv", details, fieldList, charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getEcMeetingList() throws WafException {
        logger.debug("getEcMeetingList");

        Set<Integer> set = new HashSet<Integer>();
//        set.add(22122); //kmeritsz
//        set.add(18827); // pland
        int siteId = 0;
        String siteName = "";
//        set.add(siteId);
//        set.add(20997); // moma1
//        set.add(21002); // moma2
        List<EcMeetingDetail> details = edrService.getEcMeetingList(set, 2016, 6);

//        String title = "费用详单";
        Class<EcMeetingDetail> c = EcMeetingDetail.class;
        List<Object[]> fieldList = new ArrayList<Object[]>();
        fieldList.add(new Object[]{BeanHelper.getGetMethod("siteId", c), "站点ID"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("siteName", c), "站点名称"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("confId", c), "会议ID"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("confName", c), "会议主题"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("startTime", c), "开始时间"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("endTime", c), "结束时间"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("hostId", c), "主持人ID"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("hostName", c), "主持人姓名"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("hostEmail", c), "主持人邮件"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("duration", c), "与会时长"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("peakAttendee", c), "参会人峰值"});

        try {
//            FileUtil.exportExcel( "f:/memo.xls", fieldList, details);
            String charset = "GBK";
            FileUtil.exportCSV("f:/EcMeetingList-" + siteName + "-" + charset + "-" + siteId+ ".csv", details, fieldList, charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getGenerateBill() throws WafException {
        Set<Integer> sites = new HashSet<Integer>();
        sites.add(22122);
        BillPdfBean bill = edrService.generateBill(sites, 2016, 6);

        logger.debug("Bill:\n" + ToStringBuilder.reflectionToString(bill));
    }

    @Test
    public void testExportPdf() throws WafException {
        String pdfId = "578db9486fa26dcd9dff1862"; //"578d93a36fa2062473fbe54d";
        edrService.exportPdf("f:/log/exportpdf.pdf", pdfId);
    }


    @Test
    public void getEcFee() throws WafException {
        List<EcMeetingDetail> details = edrService.getEcMeetingList(null, 2016, 6);
        Set<Integer> chargeByTimesSites = new HashSet<Integer>();
        chargeByTimesSites.add(22087);
        chargeByTimesSites.add(18707);
        Map<Integer, BigDecimal>  fees = edrService.getEcFee(details, chargeByTimesSites);

        for(Integer l: fees.keySet()){
            logger.debug(l + " : " + fees.get(l));
        }
    }
    private void getFee(){
        long duration = 200;
        long[] package1 = new long[]{200, 3000};
        long[] package2 = new long[]{300, 3000};

    }

    @Test
    public void testTest() throws WafException {
        List<String> arrayList = new ArrayList<String>();
        for(int i=0,n=10; i<n; i++){
            arrayList.add(String.valueOf(i));
        }
    }
*/
//    @Test
//    public void testGetMeetingsPortsByCustomerIdAndSiteIdAndBillPeriod(){
//        String siteName = "kmeritsz";
//        String customerId = "1890";
//        int billPeriod = 201608;
//        List<PortsOverflowDetail> portsOverflowDetails = edrService.getMeetingsPortForMaxPorts(customerId, siteName, billPeriod);
//        for(PortsOverflowDetail detail: portsOverflowDetails){
//            logger.debug(ToStringBuilder.reflectionToString(detail));
//        }
//    }

//    @Test
//    public void testGetMapOfSitesPort() throws Exception{
//
//        String start = "2016-07-01 00:00:00";
//        String end = "2016-08-01 00:00:00";
//        String customerId = "5522";
//        String siteName = "joyplus";
//        PortsUsageBean portsUsageBean = edrService.getPortsUsageSetting(customerId, siteName, DateUtils.parseFullDate(start), DateUtils.parseFullDate(end));
//        portsUsageBean.setBillPeriod(201607);
//
//        edrService.calculateSitePortsUsage(portsUsageBean, DateUtils.parseFullDate(start), DateUtils.parseFullDate(end));
//        logger.debug("Ports:" + CommonUtil.getJsonString(portsUsageBean));
//    }

//    @Test
//    public void testGetSitesPortOfCustomer() throws Exception{
//
//        String customerId = "1890";
//        int billPeriod = 201609;
//        Date[] date = DateUtils.getStartEndDateByBillPeriod(billPeriod);
//        List<WebExSite> siteList = webExSiteService.listSitesOfCustomer(customerId);
//        for(WebExSite site: siteList){
//            if(!site.getState().equals("IN_EFFECT")){
//                continue;
//            }
//            PortsUsageBean portsUsageBean = edrService.getPortsUsageSetting(customerId, site.getSiteName(), date[0], date[1]);
//            if(portsUsageBean == null){
//                logger.error( site.getSiteName() + " 没有购买 PORTS!");
//                continue;
//            }
//            portsUsageBean.setBillPeriod(billPeriod);
//
//            edrService.calculateSitePortsUsage(portsUsageBean, date[0], date[1]);
////            logger.debug("Ports:" + CommonUtil.getJsonString(portsUsageBean));
//            logger.debug("Site: " + site.getSiteName() + "; Order Ports:" + portsUsageBean.getUsedPortsAmount() + " Use Ports:" + portsUsageBean.getUsedPortsAmount());
//        }
//
//    }

//    @Test
//    public void getSiteActualPorts(){
//
//        String start = "2016-09-01 00:00:00";
//        String end = "2016-10-01 00:00:00";
//        Date startDate = DateUtils.parseFullDate(start);
//        Date endDate = DateUtils.parseFullDate(end);
//        List<PortsUsageBean> portsSetting = edrService.getPortsSetting(startDate, endDate);
//        for(PortsUsageBean portsBean: portsSetting){
//            edrService.calculateSitePortsUsage(portsBean, startDate, endDate);
//        }
//        edrService.exportExcel("Ports Usage", "/ports使用清单.xls", portsSetting);
//
//        List<PortsUsageBean> portsOverDetail = new ArrayList<PortsUsageBean>();
//        for(PortsUsageBean portsBean: portsSetting){
//            if(portsBean.getOrderPortsAmount() < portsBean.getUsedPortsAmount()){
//                portsOverDetail.add(portsBean);
//            }
//        }
//        edrService.exportExcel("Ports Usage", "/ports溢出清单.xls", portsOverDetail);
//    }

    @Test
    public void getMaxPorts() throws Exception{
        String customerId = "4501";
        String siteName = "avon";
        int ports = edrService.getMaxPorts(customerId, siteName, 201609);

//        edrService.calculateSitePortsUsage(portsUsageBean, DateUtils.parseFullDate(start), DateUtils.parseFullDate(end));
        logger.debug("Ports:" + ports);
    }
}