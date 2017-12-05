package com.kt.service;

import com.kt.biz.bean.CmrPortsUsageBean;
import com.kt.biz.bean.PortsUsageBean;
import com.kt.biz.bean.StorageUsageBean;
import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.model.charge.impl.PSTNPersonalCharge;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayByPorts;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayByTotalAttendees;
import com.kt.biz.model.charge.impl.WebExStorageMonthlyOverflowPay;
import com.kt.biz.types.BizType;
import com.kt.common.exception.ApiException;
import com.kt.common.util.BeanHelper;
import com.kt.entity.mysql.billing.BssStorageUsageLog;
import com.kt.entity.mysql.billing.PortsOverflowDetail;
import com.kt.entity.mysql.crm.Customer;
import com.kt.repo.edr.EdrRepository;
import com.kt.repo.edr.bean.MeetingUserSummary;
import com.kt.repo.edr.bean.MeetingUserTime;
import com.kt.repo.edr.bean.WbxSite;
import com.kt.repo.mysql.batch.CustomerRepository;
import com.kt.repo.mysql.billing.BssStorageUsageLogRepository;
import com.kt.repo.mysql.billing.PortsOverflowDetailRepository;
import com.kt.util.DateUtils;
import com.kt.util.FileUtil;
import com.kt.util.SortUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by jianf on 2016/7/1.
 */
@Service
public class EdrService {
    private static final Log LOGGER = LogFactory.getLog(EdrService.class);
    private final static int UTC_HOUR_OFFSET = 8;
    @Autowired
    private EdrRepository edrRepository;
    @Autowired
    private PortsOverflowDetailRepository portsOverflowDetailRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BssStorageUsageLogRepository bssStorageUsageLogRepository;

    public int getMaxPorts(String customerId, String siteName, int billPeriod) throws Exception{
        WbxSite site = edrRepository.getWbxSiteBySiteName(siteName);
        if(site == null){
            LOGGER.error("站点 [" + siteName + "] 不存在");
            throw new Exception("Site " + siteName + " is unavailable!");
        }
        PortsOverflowDetail detail = portsOverflowDetailRepository.findFirstByCustomerIdAndSiteIdAndBillPeriod(customerId, site.getSiteId(), billPeriod);;
        if(detail == null){
            return 0;
        }else{
            return detail.getPeakNumber();
        }
    }

    public int getMaxStorage(String customerId, String siteName, int billPeriod) throws Exception{
        BssStorageUsageLog bssStorageUsageLog = bssStorageUsageLogRepository.getByCustomerIdAndSiteNameAndAccountPeriod(customerId, siteName.toLowerCase(), billPeriod);
        if(bssStorageUsageLog != null){
            return (int)bssStorageUsageLog.getStorageUsedSize();
        }else{
            return 0;
        }
    }

    public int getMaxCmrPorts(String customerId, String siteName, int billPeriod) throws Exception{
        WbxSite site = edrRepository.getWbxSiteBySiteName(siteName);
        if(site == null){
            LOGGER.error("站点 [" + siteName + "] 不存在");
            throw new Exception("Site " + siteName + " is unavailable!");
        }
//        List<CsmCmrPortsOverflowDetail> detail = reportService.getCmrPortsOverflowDetailsByCustomerIdAndSiteId(customerId, site.getSiteId(), billPeriod);;
//        if(detail != null && detail.size() > 0){
//            return detail.get(0).getPeakNumber();
//        }else{
//            return 0;
//        }
        return 0;
    }

    private List<PortsOverflowDetail> getMeetingsPortForMaxPorts(String customerId, String siteName, int billPeriod){
        WbxSite site = edrRepository.getWbxSiteBySiteName(siteName);
        if(site == null){
            LOGGER.error("站点 [" + siteName + "] 不存在");
        }
        return portsOverflowDetailRepository.findMeetingsPortsByCustomerIdAndSiteIdAndBillPeriod(customerId, site.getSiteId(), billPeriod);
    }

    private List<PortsOverflowDetail> getAttendeeInfoDetailForMaxPorts(String customerId, String siteName, int billPeriod){
        WbxSite site = edrRepository.getWbxSiteBySiteName(siteName);
        if(site == null){
            LOGGER.error("站点 [" + siteName + "] 不存在");
        }
        return portsOverflowDetailRepository.findPortsOverflowDetailByCustomerIdAndSiteIdAndBillPeriod(customerId, site.getSiteId(), billPeriod);
    }

    public CmrPortsUsageBean getCmrPortsFee(Date startDate, Date endDate, WebExConfMonthlyPayByPorts portsSetting, BizType bizType, AccountPeriod accountPeriod, boolean renew){
        if(startDate == null || endDate == null || portsSetting == null || bizType == null || accountPeriod == null){
            throw new ApiException("无效的参数！");
        }
        /*
        订购起讫时间  Order
        Date startDate, Date endDate

        资费详情      WebExConfMonthlyPayByPorts
        站点

        WEBEX SERVICE 类型   BizType

        账期     201607   AccountPeriod
         */
        Date startPeriodTime = accountPeriod.beginOfThisPeriod();
        Date endPeriodTime = accountPeriod.endOfThisPeriod();
        Date edrStartTime = null;
        Date edrEndTime = null;
        if(startDate.before(startPeriodTime)){
            edrStartTime = startPeriodTime;
        }else{
            edrStartTime = startDate;
        }
        if(endDate.before(endPeriodTime)){
            edrEndTime = endDate;
        }else{
            edrEndTime = endPeriodTime;
        }

        WbxSite site = edrRepository.getWbxSiteBySiteName(portsSetting.getSiteName());
        if(site == null ||site.getSiteId() == 0){
            LOGGER.error("站点 [" + portsSetting.getSiteName() + "] 不存在");
        }

//        for(WbxSite s: sites) {
//            siteIdSet.add(s.getSiteId());
//        }
        int siteId = site.getSiteId();
        Set<Integer> siteIdSet = new HashSet<Integer>();
        siteIdSet.add(siteId);


//        final int offset = -8;
//        Calendar c = Calendar.getInstance();
//        c.setTime(edrStartTime);
//        c.add(Calendar.HOUR, offset);
//        edrStartTime = c.getTime();
//
//        c.setTime(edrEndTime);
//        c.add(Calendar.HOUR, offset);
//        edrEndTime = c.getTime();

        Customer customer = customerRepository.findFirstCustomerBySiteName(site.getSiteName());
        CmrPortsUsageBean portsUsageBean =  new CmrPortsUsageBean();
        portsUsageBean.setServiceType(bizType);
        portsUsageBean.setBillPeriod(accountPeriod.toInt());
        portsUsageBean.setSiteName(site.getSiteName());
        portsUsageBean.setSiteId(site.getSiteId());

        portsUsageBean.setCustomerId(customer.getPid());
        portsUsageBean.setCustomerName(customer.getDisplayName());

//        List<CsmCmrPortsOverflowDetail> detailList = reportService.getCmrPortsOverflowDetailsByCustomerIdAndSiteId(customer.getPid(), site.getSiteId(), accountPeriod.toInt());;
//        portsUsageBean.setPortsOverflowDetail(detailList);
//        if(detailList != null && detailList.size()>0){
//            CsmCmrPortsOverflowDetail detail = detailList.get(0);
//            portsUsageBean.setUsedPortsAmount(detail.getPeakNumber());
//        }
        int currentPos = portsUsageBean.getUsedPortsAmount();
//        if(portsDataMap.get(siteId)!= null){
//            currentPos = portsDataMap.get(siteId);
//        }
        int ports = portsSetting.getPorts();
        BigDecimal fee = new BigDecimal(0);
        if(ports > 0 && currentPos > ports){
            fee = new BigDecimal(portsSetting.getOverflowUnitPrice()).multiply(new BigDecimal(currentPos - ports));
        }

        portsUsageBean.setPortsFee(fee);

        return portsUsageBean;
    }

    public StorageUsageBean getStoragePortsFee(WebExStorageMonthlyOverflowPay storageSetting, BizType bizType, AccountPeriod accountPeriod){

        Customer customer = customerRepository.findFirstCustomerBySiteName(storageSetting.getSiteName());
        StorageUsageBean usageBean =  new StorageUsageBean();
        usageBean.setServiceType(bizType);
        usageBean.setBillPeriod(accountPeriod.toInt());
        usageBean.setSiteName(storageSetting.getSiteName());
        usageBean.setCustomerId(customer.getPid());

        int storageSize = storageSetting.getStorageSize();
        BssStorageUsageLog bssStorageUsageLog = bssStorageUsageLogRepository.getByCustomerIdAndSiteNameAndAccountPeriod(customer.getPid(), usageBean.getSiteName().toLowerCase(), accountPeriod.toInt());
        BigDecimal fee = new BigDecimal(0);
        if(bssStorageUsageLog != null){
            usageBean.setOrderStorageAmount(storageSize);
            usageBean.setUsedStorageAmount((int)bssStorageUsageLog.getStorageUsedSize());
            if(storageSize > 0 && bssStorageUsageLog.getStorageUsedSize() > storageSize){

                int amount = (int)bssStorageUsageLog.getStorageUsedSize() - storageSize;
                if(amount%5 == 0 ){
                    amount = amount/5;
                }else{
                    amount = amount/5 + 1;
                }

                fee = new BigDecimal(storageSetting.getOverflowUnitPrice()).multiply(new BigDecimal(amount));
            }
        }

        usageBean.setStorageFee(fee);

        return usageBean;

    }

    public PortsUsageBean getTotalAttendeesFee(Date startDate, Date endDate, WebExConfMonthlyPayByTotalAttendees portsSetting, BizType bizType, AccountPeriod accountPeriod, boolean renew){
        if(startDate == null || endDate == null || portsSetting == null || bizType == null || accountPeriod == null){
            throw new ApiException("无效的参数！");
        }
        /*
        订购起讫时间  Order
        Date startDate, Date endDate

        资费详情      WebExConfMonthlyPayByPorts
        站点

        WEBEX SERVICE 类型   BizType

        账期     201607   AccountPeriod
         */
        Date startPeriodTime = accountPeriod.beginOfThisPeriod();
        Date endPeriodTime = accountPeriod.endOfThisPeriod();
        Date edrStartTime = null;
        Date edrEndTime = null;
        if(startDate.before(startPeriodTime)){
            edrStartTime = startPeriodTime;
        }else{
            edrStartTime = startDate;
        }
        if(endDate.before(endPeriodTime)){
            edrEndTime = endDate;
        }else{
            edrEndTime = endPeriodTime;
        }

        WbxSite site = edrRepository.getWbxSiteBySiteName(portsSetting.getSiteName());
        if(site == null ||site.getSiteId() == 0){
            LOGGER.error("站点 [" + portsSetting.getSiteName() + "] 不存在");
        }
        int siteId = site.getSiteId();
        Set<Integer> siteIdSet = new HashSet<Integer>();
        siteIdSet.add(siteId);

        Customer customer = customerRepository.findFirstCustomerBySiteName(site.getSiteName());
        PortsUsageBean portsUsageBean =  new PortsUsageBean();
        portsUsageBean.setServiceType(bizType);
        portsUsageBean.setBillPeriod(accountPeriod.toInt());
        portsUsageBean.setSiteName(site.getSiteName());
        portsUsageBean.setSiteId(site.getSiteId());

        portsUsageBean.setCustomerId(customer.getPid());

        calculateSiteAttendees(portsUsageBean, edrStartTime, edrEndTime);
        int currentPos = portsUsageBean.getUsedPortsAmount();
        int ports = portsSetting.getPorts();
        BigDecimal fee = new BigDecimal(0);
        if(ports > 0 && currentPos > ports){
            fee = new BigDecimal(portsSetting.getOverflowUnitPrice()).multiply(new BigDecimal(currentPos - ports));
        }

        portsUsageBean.setPortsFee(fee);

        return portsUsageBean;

    }

    private void calculateSiteAttendees(PortsUsageBean portsUsageBean, Date startTime, Date endTime){

        Set<Integer> companyIds = new HashSet<Integer>();
        BizType bizType = portsUsageBean.getServiceType();
        if(bizType.equals(BizType.WEBEX_EC)){
            companyIds.add(6);
        }else if(bizType.equals(BizType.WEBEX_MC)){
            companyIds.add(1);
        }else if(bizType.equals(BizType.WEBEX_SC)){
            companyIds.add(9);
        }else if(bizType.equals(BizType.WEBEX_TC)){
            companyIds.add(7);
        }else if(bizType.equals(BizType.WEBEX_EE)){
//            companyIds.add(1);
//            companyIds.add(6);
//            companyIds.add(7);
            companyIds.add(10);
        }else {
//            return new BigDecimal(0);
        }

        if(portsUsageBean.getSiteId() == 0 && StringUtils.isNotBlank(portsUsageBean.getSiteName())){
            WbxSite site = edrRepository.getWbxSiteBySiteName(portsUsageBean.getSiteName());
            portsUsageBean.setSiteId(site.getSiteId());
        }
        int attendeeCount = edrRepository.getMeetingUserCount(startTime, endTime, portsUsageBean.getSiteId(), companyIds);
        Calendar c = Calendar.getInstance();
        c.setTime(startTime);
        portsUsageBean.setBillPeriod(c.get(Calendar.YEAR) * 100 + c.get(Calendar.MONTH) + 1);
        portsUsageBean.setUsedPortsAmount(attendeeCount);
        portsUsageBean.setNumberOfOverflows(portsUsageBean.getUsedPortsAmount()>portsUsageBean.getOrderPortsAmount()?portsUsageBean.getUsedPortsAmount()-portsUsageBean.getOrderPortsAmount():0 );
    }

    public float getPersonalPstnFee(Date startDate, Date endDate, PSTNPersonalCharge personalChargeSetting, BizType bizType, AccountPeriod accountPeriod){
        return 0;
    }

    public PortsUsageBean getPortsFee(Date startDate, Date endDate, WebExConfMonthlyPayByPorts portsSetting, BizType bizType, AccountPeriod accountPeriod, boolean renew){
        if(startDate == null || endDate == null || portsSetting == null || bizType == null || accountPeriod == null){
            throw new ApiException("无效的参数！");
        }
        /*
        订购起讫时间  Order
        Date startDate, Date endDate

        资费详情      WebExConfMonthlyPayByPorts
        站点

        WEBEX SERVICE 类型   BizType

        账期     201607   AccountPeriod
         */
        Date startPeriodTime = accountPeriod.beginOfThisPeriod();
        Date endPeriodTime = accountPeriod.endOfThisPeriod();
        Date edrStartTime = null;
        Date edrEndTime = null;
        if(startDate.before(startPeriodTime)){
            edrStartTime = startPeriodTime;
        }else{
            edrStartTime = startDate;
        }
        if(endDate.before(endPeriodTime)){
            edrEndTime = endDate;
        }else{
            edrEndTime = endPeriodTime;
        }

        WbxSite site = edrRepository.getWbxSiteBySiteName(portsSetting.getSiteName());
        if(site == null ||site.getSiteId() == 0){
            LOGGER.error("站点 [" + portsSetting.getSiteName() + "] 不存在");
        }

//        for(WbxSite s: sites) {
//            siteIdSet.add(s.getSiteId());
//        }
        int siteId = site.getSiteId();
        Set<Integer> siteIdSet = new HashSet<Integer>();
        siteIdSet.add(siteId);


//        final int offset = -8;
//        Calendar c = Calendar.getInstance();
//        c.setTime(edrStartTime);
//        c.add(Calendar.HOUR, offset);
//        edrStartTime = c.getTime();
//
//        c.setTime(edrEndTime);
//        c.add(Calendar.HOUR, offset);
//        edrEndTime = c.getTime();

        Customer customer = customerRepository.findFirstCustomerBySiteName(site.getSiteName());
        PortsUsageBean portsUsageBean =  new PortsUsageBean();
        portsUsageBean.setServiceType(bizType);
        portsUsageBean.setBillPeriod(accountPeriod.toInt());
        portsUsageBean.setSiteName(site.getSiteName());
        portsUsageBean.setSiteId(site.getSiteId());

        portsUsageBean.setCustomerId(customer.getPid());

        calculateSitePortsUsage(portsUsageBean, edrStartTime, edrEndTime);
        int currentPos = portsUsageBean.getUsedPortsAmount();
//        if(portsDataMap.get(siteId)!= null){
//            currentPos = portsDataMap.get(siteId);
//        }
        int ports = portsSetting.getPorts();
        BigDecimal fee = new BigDecimal(0);
        if(ports > 0 && currentPos > ports){
            fee = new BigDecimal(portsSetting.getOverflowUnitPrice()).multiply(new BigDecimal(currentPos - ports));
        }

        portsUsageBean.setPortsFee(fee);

        return portsUsageBean;
    }
    private String convert2UTF8(String iso){
        try {
            return new String(iso.getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            LOGGER.error("convert charset fail.", e);
        }
        return null;
    }

    private void calculateStoragePortsUsage(StorageUsageBean portsUsageBean, Date startTime, Date endTime) {

    }
    private void calculateSitePortsUsage(PortsUsageBean portsUsageBean, Date startTime, Date endTime) {



        Set<Integer> companyIds = new HashSet<Integer>();
        BizType bizType = portsUsageBean.getServiceType();
        if(bizType.equals(BizType.WEBEX_EC)){
            companyIds.add(6);
        }else if(bizType.equals(BizType.WEBEX_MC)){
            companyIds.add(1);
        }else if(bizType.equals(BizType.WEBEX_SC)){
            companyIds.add(9);
        }else if(bizType.equals(BizType.WEBEX_TC)){
            companyIds.add(7);
        }else if(bizType.equals(BizType.WEBEX_EE)){
//            companyIds.add(1);
//            companyIds.add(6);
//            companyIds.add(7);
            companyIds.add(10);
        }else {
//            return new BigDecimal(0);
        }

        Date now = new Date();
        if(portsUsageBean.getSiteId() == 0 && StringUtils.isNotBlank(portsUsageBean.getSiteName())){
            WbxSite site = edrRepository.getWbxSiteBySiteName(portsUsageBean.getSiteName());
            portsUsageBean.setSiteId(site.getSiteId());
        }
        List<MeetingUserSummary> meetingUserList = edrRepository.getMeetingUserList(startTime, endTime, portsUsageBean.getSiteId(), companyIds);
        List<MeetingUserTime> meetingUserTime = new ArrayList<MeetingUserTime>();

        for(MeetingUserSummary s: meetingUserList){
            MeetingUserTime userStartTime = new MeetingUserTime();
            MeetingUserTime userEndTime = new MeetingUserTime();
            userEndTime.setDate(s.getEndTime());
            userEndTime.setFlag(-1);
            userEndTime.setSiteId(s.getSiteId());
            userEndTime.setConfId(s.getConfId());

            userStartTime.setDate(s.getStartTime());
            userStartTime.setFlag(1);
            userStartTime.setSiteId(s.getSiteId());
            userStartTime.setConfId(s.getConfId());

            meetingUserTime.add(userStartTime);
            meetingUserTime.add(userEndTime);

        }
//        LOGGER.debug(meetingUserTime.size());
        SortUtil.anyProperSort(meetingUserTime, "date", true);

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        Map<Integer, Integer> maxMap = new HashMap<Integer, Integer>();
        Map<Integer, Date> dateMap = new HashMap<Integer, Date>();
        for(MeetingUserTime t: meetingUserTime){
//            LOGGER.debug(ToStringBuilder.reflectionToString(t));
            int siteId = t.getSiteId();
            int flag = t.getFlag();
            Integer ports = map.get(siteId);
            int currentPost = 0;
            if(ports == null){
                currentPost = (flag>0 ? flag:0);
                map.put(siteId, currentPost);
            }else{
                currentPost = flag + ports;
                map.put(siteId, currentPost);
            }


            Integer maxPorts = maxMap.get(siteId);
            if(maxPorts == null){
                LOGGER.debug("PORTS[" + currentPost + "] confid:" + t.getConfId() + "; Time:" + DateUtils.formatDateTime(t.getDate()));
                maxMap.put(siteId, currentPost);
                dateMap.put(siteId, t.getDate());
            }else{
//                if(currentPost > maxPorts) {
//                currentPost = (currentPost > maxPorts ? currentPost:maxPorts);
                if(currentPost >= maxPorts){
                    final int offset = 8;
                    Calendar c = Calendar.getInstance();
                    c.setTime(t.getDate());
                    c.add(Calendar.HOUR, offset);

                    LOGGER.debug("PORTS[" + currentPost + "] confid:" + t.getConfId() + "; Time:" + DateUtils.formatDateTime(c.getTime()));
                }
                if(currentPost > maxPorts) {
                    maxMap.put(siteId, currentPost);
                    dateMap.put(siteId, t.getDate());
                }
            }
        }

//        LOGGER.debug("Meeting Site count:" + maxMap.size());
        Date timeOfMaxOverflow = dateMap.get(portsUsageBean.getSiteId());
        if(timeOfMaxOverflow != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(timeOfMaxOverflow);
            c.add(Calendar.HOUR, UTC_HOUR_OFFSET);
            portsUsageBean.setPeakTime(c.getTime());
            portsUsageBean.setBillPeriod(c.get(Calendar.YEAR) * 100 + c.get(Calendar.MONTH) + 1);
            List<PortsOverflowDetail> overflowDetails = createMeetingUserDetailForPortsOverflow(portsUsageBean, companyIds);
//            portsUsageBean.setPortsOverflowDetail(overflowDetails);
            long seconds = (new Date().getTime() - now.getTime());
            LOGGER.info("Cost time:" + seconds + (" ms!"));
            portsUsageBean.setUsedPortsAmount(maxMap.get(portsUsageBean.getSiteId()));
            portsUsageBean.setNumberOfOverflows(portsUsageBean.getUsedPortsAmount()>portsUsageBean.getOrderPortsAmount()?portsUsageBean.getUsedPortsAmount()-portsUsageBean.getOrderPortsAmount():0 );
        }
    }

//    private int getBillPeriodByAccountDate(Date date){
//        Calendar c = Calendar.getInstance();
//        c.setTime(date);
//        return c.get(Calendar.YEAR) * 100 + c.get(Calendar.MONTH ) + 1;
//    }

    private List<PortsOverflowDetail> createMeetingUserDetailForPortsOverflow(PortsUsageBean portsUsageBean, Set<Integer> companyIds){
        Set<Integer> siteIds = new HashSet<Integer>();
        siteIds.add(portsUsageBean.getSiteId());
        int siteId = portsUsageBean.getSiteId();
        List<PortsOverflowDetail> meetingUserList = edrRepository.getMeetingUserDetailsForPortsOverflow(portsUsageBean.getPeakTime(), portsUsageBean.getPeakTime(), siteIds, companyIds);
        Map<Long, Integer> confPeakNumber = new HashMap<Long, Integer>();
        for(PortsOverflowDetail detail: meetingUserList){
            if(confPeakNumber.get(detail.getConfId()) == null){
                confPeakNumber.put(detail.getConfId(), 1);
            }else{
                confPeakNumber.put(detail.getConfId(), confPeakNumber.get(detail.getConfId()) + 1);
            }
        }

        WbxSite site = edrRepository.getWbxSiteBySiteId(siteId);
        if(site == null){
            LOGGER.error("站点 [" + siteId + "] 不存在");
        }
        Customer customer = customerRepository.findFirstCustomerBySiteName(site.getSiteName());
        int ports = meetingUserList.size();
        Date now = new Date();
        for(PortsOverflowDetail detail: meetingUserList){
            detail.setOrderedPorts(portsUsageBean.getOrderPortsAmount());
            detail.setPeakNumber(ports);
            detail.setPeakTime(portsUsageBean.getPeakTime());
            detail.setBillPeriod(portsUsageBean.getBillPeriod());
            detail.setCreateTime(now);
            Date startDate = detail.getStartTime();
            Date endDate = detail.getEndTime();
            Calendar c = Calendar.getInstance();
            c.setTime(startDate);
            c.add(Calendar.HOUR, UTC_HOUR_OFFSET);
            startDate = c.getTime();

            c.setTime(endDate);
            c.add(Calendar.HOUR, UTC_HOUR_OFFSET);
            endDate = c.getTime();

            detail.setStartTime(startDate);
            detail.setEndTime(endDate);
            detail.setMeetingPeakNumber(confPeakNumber.get(detail.getConfId()));
            detail.setCustomerId(customer.getPid());
            detail.setCustomerName(customer.getDisplayName());

            detail.setConfName(this.convert2UTF8(detail.getConfName()));
            detail.setUserName(this.convert2UTF8(detail.getUserName()));
            detail.setHostName(this.convert2UTF8(detail.getHostName()));
        }

        portsOverflowDetailRepository.deleteByCustomerIdAndSiteIdAndBillPeriod(customer.getPid(), site.getSiteId(), portsUsageBean.getBillPeriod());
        meetingUserList = portsOverflowDetailRepository.save(meetingUserList);
        portsUsageBean.setPortsOverflowDetail(meetingUserList);

        return meetingUserList;
    }

    private class GetStartEndTime {
//        private static final int UTC_HOUR_OFFSET = -8;
        private int year;
        private int month;
        private Date startDate;
        private Date endDate;

        public GetStartEndTime(int year, int month) {
            this.year = year;
            this.month = month;
        }

        public Date getStartDate() {
            return startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public GetStartEndTime invoke() {
            int endTimeYear = year;
            int endTimeMonth = month + 1;
            if ( endTimeMonth == 13){
                endTimeMonth = 1;
                endTimeYear = year + 1;
            }

            String startTime = year + "-" + ( month <= 9 ? month : ("0" + month))  + "-01 00:00:00";
            String endTime = endTimeYear + "-" + ( endTimeMonth <=9 ? endTimeMonth: ("0" + endTimeMonth))  + "-01 00:00:00";
            startDate = DateUtils.parseDate(startTime);
            endDate = DateUtils.parseDate(endTime);

            return this;
        }
    }

    private PortsUsageBean getPortsUsageSetting(String customerId, String siteName, Date startTime, Date endTime) throws Exception{
        return edrRepository.getPortsUsageSetting(customerId, siteName, startTime, endTime);
    }

    private WbxSite getWbxSiteBySiteName(String siteName){
        return edrRepository.getWbxSiteBySiteName(siteName);
    }

    private List<PortsUsageBean> getPortsSetting(Date startDate, Date endDate){
        return portsOverflowDetailRepository.getSitePortsSetting(startDate, endDate);
    }

    private void exportExcel(String title, String filePath, List<PortsUsageBean> portsUsageBeans){

        Class<PortsUsageBean> c = PortsUsageBean.class;
        List<Object[]> fieldList = new ArrayList<Object[]>();

        fieldList.add(new Object[]{BeanHelper.getGetMethod("customerName", c), "customerName"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("customerCode", c), "customerCode"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("customerId", c), "customerId"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("siteName", c), "siteName"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("siteId", c), "siteId"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("productName", c), "productName"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("effectiveStartDate", c), "effectiveStartDate"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("effectiveEndDate", c), "effectiveEndDate"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("serviceType", c), "serviceType"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("orderPortsAmount", c), "orderPortsAmount"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("usedPortsAmount", c), "usedPortsAmount"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("numberOfOverflows", c), "numberOfOverflows"});
        fieldList.add(new Object[]{BeanHelper.getGetMethod("peakTime", c), "peakTime"});

        try {
            FileUtil.exportExcel(filePath,title, fieldList, portsUsageBeans, false);
        } catch (Exception e) {
            LOGGER.error("export excel file error:", e);
        }
    }
}
