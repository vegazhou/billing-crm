package com.kt.service;

import com.kt.biz.bean.GenericChargeSchemeBean;
import com.kt.biz.bean.TelecomProductBean;
import com.kt.biz.model.biz.AbstractBizScheme;
import com.kt.biz.model.biz.impl.BizWebExMC;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.TelecomCharge;
import com.kt.biz.model.common.SchemeKeys;
import com.kt.biz.site.LanguageMatrix;
import com.kt.biz.site.WebExSitePrimaryFields;
import com.kt.biz.types.*;
import com.kt.biz.types.TimeZone;
import com.kt.entity.mysql.batch.ProvHostConfig;
import com.kt.entity.mysql.batch.WbxProvTask;
import com.kt.entity.mysql.crm.*;
import com.kt.exception.*;
import com.kt.repo.mysql.batch.ChargeSchemeAttributeRepository;
import com.kt.repo.mysql.batch.ProvHostConfigRepository;
import com.kt.repo.mysql.batch.WbxProvTaskRepository;
import com.kt.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Administrator on 2016/6/3.
 */
@Service
public class TeleOrderService {

    private static final Logger LOGGER = Logger.getLogger(TeleOrderService.class);

    private final static String PARAMETER_ACTION = "Action";
    private final static String PARAMETER_ENTERPRISE_CODE = "EnterpriseCode";
    private final static String PARAMETER_ENTERPRISE_NAME = "EnterpriseName";
    private final static String PARAMETER_EMAIL = "Email";
    private final static String PARAMETER_FULL_NAME = "Name";
    private final static String PARAMETER_ACCOUNT_TYPE = "AccountType";
    private final static String PARAMETER_SERVICE_TYPE = "ServiceType";
    private final static String PARAMETER_ATTENDEE_CAPACITY = "AttendeeCapacity";
    private final static String PARAMETER_PAY_PERIOD = "PayPeriod";
    private final static String PARAMETER_START_DATE = "StartDate";
    private final static String PARAMETER_BUY_MONTHS = "TimeLimit";
    private final static String PARAMETER_TERMINATE_DATE = "TerminateDate";
    private final static String PARAMETER_PASSWORD = "Password";
    private final static String PARAMETER_PRICE = "Price";
    public final static String PARAMETER_TD_TYPE = "TelDomainType";
    public final static String PARAMETER_SITE_NAME = "SiteName";

    public final static String PARAMETER_IS_ADMIN_ACCOUNT = "IsAdminAccount";
    public final static String PARAMETER_LICENSE_VOLUME = "LicenseVolume";
    public final static String PARAMETER_TELECOM_ADMIN_EMAIL = "TeleComAdminEmail";
    public final static String PARAMETER_TELECOM_ADMIN_PWD = "TeleComAdminPwd";
    public final static String PARAMETER_TELECOM_ADMIN_ORDERIDS = "OrderIds";
    public final static String PARAMETER_TELECOM_ADMIN_CONTRACTID = "ContractId";

    private final static String CONTRACT_PREFIX="Contract-";

    private final static String SITE_CONTRACT_PREFIX="SiteContract-";

    @Autowired
    ContractService contractService;

    @Autowired
    CustomerService customerService;

    @Autowired
    OrderService orderService;

    @Autowired
    WebExSiteService webExSiteService;

    @Autowired
    ChargeSchemeService chargeSchemeService;

    @Autowired
    BizSchemeService bizSchemeService;

    @Autowired
    ProductService productService;

    @Autowired
    ChargeSchemeAttributeRepository chargeSchemeAttributeRepository;

    @Autowired
    ProvHostConfigRepository provHostConfigRepository;

    @Autowired
    private WbxProvTaskRepository wbxProvTaskRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createOrder(Map<String, String> parameterMap, String telecomCustomerId, String telecomSalesManId,
                            String telecomProdId, String pstnProdId)
            throws WafException,OrderIncompleteException,OrderCollisionsDetectedException {

        if(this.isProvAdminAccount(parameterMap)){  //Provision site
            createSiteOrder(parameterMap, telecomCustomerId,telecomSalesManId,telecomProdId,pstnProdId);
        }else{  //Provision host
            createHostOrder(parameterMap, telecomCustomerId, telecomSalesManId, telecomProdId);
        }

    }

    private void createSiteOrder(Map<String, String> parameterMap, String telecomCustomerId, String telecomSalesManId,
                            String telecomProdId, String pstnProdId)
            throws WafException,OrderIncompleteException,OrderCollisionsDetectedException {

        if(isSiteOrderExist(parameterMap)){
            throw new OrderExistException();
        }

        String email = parameterMap.get(PARAMETER_EMAIL);

        if (StringUtils.isEmpty(email)) {
            Date date = new Date();
            email = Long.toString(date.getTime());
        }
        Contract contract = contractService.draftTelecomContract(telecomCustomerId, SITE_CONTRACT_PREFIX + email, telecomSalesManId);
        LOGGER.info("Add telecom order step1 - add draft contact: contractId=" + contract.getPid());
        if(contract!=null) {
            parameterMap.put(PARAMETER_TELECOM_ADMIN_CONTRACTID, contract.getPid());
        }

        String siteName = parameterMap.get(PARAMETER_SITE_NAME);
        boolean isPure010 = false;
        if("010".equals(parameterMap.get(PARAMETER_TD_TYPE))){
            isPure010 = true;
        }

        if(!isRenewOrder(parameterMap)) {
            WebExSiteDraft siteDraft = webExSiteService.draftSite4TelcomContract(contract.getPid(), this.getWebExSitePrimaryFields(siteName, isPure010));
            LOGGER.info("Add telecom order step2 - add draft site: siteId=" + siteDraft.getId());
        }

        String tempMonths = parameterMap.get(PARAMETER_BUY_MONTHS);
        int months = Integer.parseInt(tempMonths);
        Order mainProdOrder = orderService.placeTelecomOrder(contract.getPid(), telecomProdId,
                DateUtil.getSystemDate(), getPayInterval(parameterMap));
        LOGGER.info("Add telecom order step3 - add draft main-product order: orderId=" + mainProdOrder.getPid());
        chargeSchemeService.updateTelecomChargeScheme(mainProdOrder.getChargeId(), getGenericChargeSchemeBean4SiteMainProd(parameterMap));
        String orderIds="";
        if(mainProdOrder!=null){
            orderIds = mainProdOrder.getPid();
        }

        Order pstnOrder = orderService.placeTelecomOrder(contract.getPid(), pstnProdId,
                DateUtil.getSystemDate(), getPayInterval(parameterMap));
        LOGGER.info("Add telecom order step4 - add draft PSTN order: orderId=" + pstnOrder.getPid());
        chargeSchemeService.updateTelecomChargeScheme(pstnOrder.getChargeId(), getGenericChargeSchemeBean4SitePstnProd(parameterMap, mainProdOrder.getEffectiveEndDate()));
        if(pstnOrder!=null){
            if(orderIds.length()>0) {
                orderIds = orderIds + "," + pstnOrder.getPid();
            }else{
                orderIds = pstnOrder.getPid();
            }
        }
        parameterMap.put(PARAMETER_TELECOM_ADMIN_ORDERIDS,orderIds);

        LOGGER.info("Add telecom order step5 - update charge scheme info");
        contractService.sendTelecomContract4Approval(contract.getPid());
        LOGGER.info("Add telecom order step6 - send contract for approval");
        contractService.approveTelecom(contract.getPid());
        LOGGER.info("Add telecom order step7 - approve contract");
        contractService.finApproveTelecom(contract.getPid(),0f);
        LOGGER.info("Add telecom order successfully.");
    }

    private void createHostOrder(Map<String, String> parameterMap, String telecomCustomerId, String telecomSalesManId,
                                String telecomProdId)
            throws WafException,OrderIncompleteException,OrderCollisionsDetectedException {

        TelecomCharge telecomCharge = findChargeScheme(parameterMap);
        if(telecomCharge!=null){
            Order order = orderService.findOrderByChargeId(telecomCharge.getId());
            if(!order.getState().equals(CommonState.END_OF_LIFE.toString())) {
                throw new OrderExistException();
            }
        }

        String fullName = parameterMap.get(PARAMETER_FULL_NAME);

        if (StringUtils.isEmpty(fullName)) {
            Date date = new Date();
            fullName = Long.toString(date.getTime());
        }
        Contract contract = contractService.draftTelecomContract(telecomCustomerId, CONTRACT_PREFIX + fullName, telecomSalesManId);
        LOGGER.info("Add telecom order step1 - add draft contact: contractId=" + contract.getPid());

        String tempMonths = parameterMap.get(PARAMETER_BUY_MONTHS);
        int months = Integer.parseInt(tempMonths);
        Order order = orderService.placeTelecomOrder(contract.getPid(), telecomProdId,
                DateUtil.getSystemDate(), getPayInterval(parameterMap));
        LOGGER.info("Add telecom order step2 - add draft order: orderId=" + order.getPid());
        chargeSchemeService.updateTelecomChargeScheme(order.getChargeId(), getGenericChargeSchemeBean(parameterMap, telecomCustomerId));
        LOGGER.info("Add telecom order step3 - update charge scheme info");
        contractService.sendTelecomContract4Approval(contract.getPid());
        LOGGER.info("Add telecom order step4 - send contract for approval");
        contractService.approveTelecom(contract.getPid());
        LOGGER.info("Add telecom order successfully.");
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    //Return ChargeSchemeId
    public String updateOrder(Map<String, String> parameterMap)
            throws WafException{
        TelecomCharge telecomCharge = findChargeScheme(parameterMap);
        if(telecomCharge==null){
            LOGGER.info("Update telecom order failed!");
            throw new ChargeSchemeNotFoundException();
        }else{
            checkOrderState(telecomCharge.getId());
        }
        String displayName = parameterMap.get(PARAMETER_FULL_NAME);
        String password = parameterMap.get(PARAMETER_PASSWORD);
        if(displayName!=null && displayName.length()>0) {
            telecomCharge.setUserName(displayName);
        }
        if(password!=null & password.length()>0) {
            telecomCharge.setInitialPassword(password);
        }
        chargeSchemeService.saveChargeScheme(telecomCharge);
        LOGGER.info("Update telecom order successfully: email=" + telecomCharge.getWebexId() +
            ", siteName=" + telecomCharge.getSiteName());
        return telecomCharge.getId();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void terminateOrder(Map<String, String> parameterMap) throws WafException{

        if(this.isProvAdminAccount(parameterMap)) {  //Terminate site
            terminateSiteOrder(parameterMap);
        }else{
            terminateHostOrder(parameterMap);
        }
    }

    public void terminateHostOrder(Map<String, String> parameterMap) throws WafException{
        TelecomCharge telecomCharge = findChargeScheme(parameterMap);
        if(telecomCharge==null){
            LOGGER.info("Terminate telecom order failed!");
            throw new ChargeSchemeNotFoundException();
        }else{
            checkOrderState(telecomCharge.getId());
        }
        Order order = orderService.findOrderByChargeId(telecomCharge.getId());
        orderService.terminateTelecomOrderOnSpecificDay(order.getPid(),DateUtil.getSystemDate());
        LOGGER.info("Terminate telecom order successfully: orderId=" + order.getPid());
    }

    public void terminateSiteOrder(Map<String, String> parameterMap) throws WafException{

        if(!isSiteOrderExist(parameterMap)){
            LOGGER.info("Terminate telecom order failed!");
            throw new ChargeSchemeNotFoundException();
        }
        List<String> orderIds = getOrderIds(parameterMap);
        for(String orderId: orderIds) {
            orderService.terminateTelecomOrderOnSpecificDay(orderId, DateUtil.getSystemDate());
            LOGGER.info("Terminate telecom order successfully: orderId=" + orderId);
        }
    }

    private PayInterval getPayInterval(Map<String, String> parameterMap){
        String payPeriod = parameterMap.get(PARAMETER_PAY_PERIOD);
        if(payPeriod.trim().equals("1")){
            return PayInterval.MONTHLY;
        }else if(payPeriod.trim().equals("3")){
            return PayInterval.QUARTERLY;
        }else if(payPeriod.trim().equals("6")){
            return PayInterval.HALF_YEARLY;
        }else if(payPeriod.trim().equals("12")){
            return PayInterval.YEARLY;
        }else if(payPeriod.trim().equals("-1")){
            return PayInterval.ONE_TIME;
        }else{
            return PayInterval.MONTHLY;
        }
    }

    private void checkOrderState(String telecomChargeId) throws WafException{
        Order order = orderService.findOrderByChargeId(telecomChargeId);
        String orderProvisionState = order.getProvisionState();
        boolean isOrderProvisioned = (orderProvisionState!=null) && orderProvisionState.equals(OrderProvisionStatus.SUCCESS.toString());
        if(!isOrderProvisioned){
            throw new OrderNotProvisionException();
        }
        String orderState = order.getState();
        boolean isOrderInEffect = (orderState!=null) && orderState.equals(CommonState.IN_EFFECT.toString());
        if(!isOrderInEffect){
            throw new OrderNotInEffectException();
        }
    }

    private GenericChargeSchemeBean getGenericChargeSchemeBean4SiteMainProd(Map<String, String> parameterMap){
        GenericChargeSchemeBean bean = new GenericChargeSchemeBean();
        bean.setCommonSite(parameterMap.get(PARAMETER_SITE_NAME));
        bean.setHostAmount(Integer.parseInt(parameterMap.get(PARAMETER_LICENSE_VOLUME)));
        bean.setMonthAmount(Integer.parseInt(parameterMap.get(PARAMETER_BUY_MONTHS)));
        bean.setUnitPrice(Float.parseFloat(parameterMap.get(PARAMETER_PRICE)));
        return bean;
    }

    private GenericChargeSchemeBean getGenericChargeSchemeBean4SitePstnProd(Map<String, String> parameterMap, String endDate){
        GenericChargeSchemeBean bean = new GenericChargeSchemeBean();
        List<String> sites = new ArrayList<String>();
        sites.add(parameterMap.get(PARAMETER_SITE_NAME));
        bean.setCommonSites(sites);
        bean.setEffectiveBefore(endDate);
        return bean;
    }

    private GenericChargeSchemeBean getGenericChargeSchemeBean(Map<String, String> parameterMap, String telecomCustomerId){
        GenericChargeSchemeBean bean = new GenericChargeSchemeBean();
        bean.setEnterpriseCode(parameterMap.get(PARAMETER_ENTERPRISE_CODE));
        bean.setEnterpriseName(parameterMap.get(PARAMETER_ENTERPRISE_NAME));
        bean.setWebexId(parameterMap.get(PARAMETER_EMAIL));
        bean.setDisplayName(parameterMap.get(PARAMETER_FULL_NAME));
        String password = parameterMap.get(PARAMETER_PASSWORD);
        bean.setInitialPassword(password);
        bean.setEnterpriseCode(parameterMap.get(PARAMETER_ENTERPRISE_CODE));
//        List<WebExSite> sites = webExSiteService.listSitesOfCustomer(telecomCustomerId);
//        for(WebExSite site: sites){
//            int mcAttendeeCapacity = site.getMcAttendeeCapacity();
//            int inputCapacity = Integer.parseInt(parameterMap.get(PARAMETER_ATTENDEE_CAPACITY));
//            if(inputCapacity==mcAttendeeCapacity){
//                bean.setCommonSite(site.getSiteName());
//            }
//        }
        List<ProvHostConfig> provHostConfigs = findProvUserConfig(telecomCustomerId,
                parameterMap.get(PARAMETER_SERVICE_TYPE),
                parameterMap.get(PARAMETER_ATTENDEE_CAPACITY),
                parameterMap.get(PARAMETER_TD_TYPE),
                WbxProvTaskType.HOST.toString());
        if(provHostConfigs!=null && provHostConfigs.size()>0){
            bean.setCommonSite(provHostConfigs.get(0).getSiteName());
        }
        bean.setHostAmount(1);
        bean.setMonthAmount(Integer.parseInt(parameterMap.get(PARAMETER_BUY_MONTHS)));
        bean.setUnitPrice(Float.parseFloat(parameterMap.get(PARAMETER_PRICE)));
        return bean;
    }

    public TelecomCharge findChargeScheme(Map<String, String> parameterMap){
        String webexId = parameterMap.get(PARAMETER_EMAIL);
        String attendeeCapacity = parameterMap.get(PARAMETER_ATTENDEE_CAPACITY);
        String serviceType = parameterMap.get(PARAMETER_SERVICE_TYPE);
        String configSiteName = parameterMap.get(PARAMETER_SITE_NAME);
        List<ChargeSchemeAttribute> chargeAttributes = chargeSchemeAttributeRepository.findByNameAndValueIgnoreCase(SchemeKeys.WEBEX_ID, webexId);
        for(ChargeSchemeAttribute chargeAttribute:chargeAttributes){
            String id = chargeAttribute.getEntityId();
            try {
                AbstractChargeScheme chargeScheme = chargeSchemeService.findChargeSchemeById(id);
                if(chargeScheme instanceof TelecomCharge){
                    String siteName = ((TelecomCharge) chargeScheme).getSiteName();
                    if(siteName.toLowerCase().equals(configSiteName.toLowerCase())){
                        return ((TelecomCharge) chargeScheme);
                    }
//                    WebExSite site = webExSiteService.findBySiteName(siteName);
//                    if(site!=null) {
//                        if (serviceType.toUpperCase().equals("MC")) {
//                            if (site.getMcAttendeeCapacity() == Integer.parseInt(attendeeCapacity)) {
//                                return ((TelecomCharge) chargeScheme);
//                            }
//                        }else if(serviceType.toUpperCase().equals("SC")){
//                            if (site.getScAttendeeCapacity() == Integer.parseInt(attendeeCapacity)) {
//                                return ((TelecomCharge) chargeScheme);
//                            }
//                        }else if(serviceType.toUpperCase().equals("EC")){
//                            if (site.getEcAttendeeCapacity() == Integer.parseInt(attendeeCapacity)) {
//                                return ((TelecomCharge) chargeScheme);
//                            }
//                        }else if(serviceType.toUpperCase().equals("TC")){
//                            if (site.getTcAttendeeCapacity() == Integer.parseInt(attendeeCapacity)) {
//                                return ((TelecomCharge) chargeScheme);
//                            }
//                        }else if(serviceType.toUpperCase().equals("EE")){
//                            if (site.getEeAttendeeCapacity() == Integer.parseInt(attendeeCapacity)) {
//                                return ((TelecomCharge) chargeScheme);
//                            }
//                        }else{
//                            //当前不支持MC之外的其它Service
//                            LOGGER.info("Abnormal Telecom order - invalid service type: id=" + id);
//                        }
//                    }
                }else{//异常电信工单
                    LOGGER.info("Abnormal Telecom order: id=" + id);
                }
            } catch (WafException e) {
                LOGGER.info("Charge scheme not found: id=" + id);
            }
        }
        return null;
    }

    public List<ProvHostConfig> findAllProvUserConfig()
    {
        return provHostConfigRepository.findAll();
    }

    public List<ProvHostConfig> findProvUserConfigBySiteName(String siteName)
    {
        return provHostConfigRepository.findBySiteName(siteName);
    }

    public List<ProvHostConfig> findProvUserConfig(String customerId,String serviceName,
                                                   String attendeeCapacity,String telType,String productType){
        return provHostConfigRepository.findByCustomerIdAndServiceNameAndAttendeeCapacityAndTelTypeAndProductType(customerId,
                serviceName, attendeeCapacity, telType, productType);
    }

    public List<ProvHostConfig> findProvUserConfig(String customerId, String serviceName){
        return provHostConfigRepository.findByCustomerIdAndServiceNameOrderByAttendeeCapacityAsc(customerId,serviceName);
    }

    public List<ProvHostConfig> findProvUserConfig(String customerId, String serviceName, String attendeeCapacity){
        return provHostConfigRepository.findByCustomerIdAndServiceNameAndAttendeeCapacity(customerId,serviceName,attendeeCapacity);
    }

    public boolean isSiteOrderExist(Map<String, String> parameterMap){
        boolean bResult = false;
        String adminEmail = parameterMap.get(PARAMETER_EMAIL);
        String siteName = parameterMap.get(PARAMETER_SITE_NAME);

        List<WbxProvTask> tasks = wbxProvTaskRepository.findByTypeAndSiteNameAndHostEmailIgnoreCaseOrderByCreateTimeDesc(WbxProvTaskType.HOST.toString(),
                siteName, adminEmail);

        if(tasks.size()>0){
            String orderId=tasks.get(0).getOrderId();
            String[] orderIdArray = orderId.split(",");
            if(orderIdArray.length>0){
                try {
                    Order order = orderService.findOrderById(orderIdArray[0]);
                    boolean isTerminatedOrder = CommonState.END_OF_LIFE.toString().equals(order.getState()) ||
                            order.getEffectiveStartDate().equals(order.getEffectiveEndDate());
                    if(!isTerminatedOrder){
                       bResult = true;
                    }
                } catch (OrderNotFoundException e) {
                    LOGGER.info("Create Telcom site order: validate site order exist failed");
                }
            }
        }

        return bResult;
    }

    public boolean isRenewOrder(Map<String, String> parameterMap){
        boolean bResult = false;
        String adminEmail = parameterMap.get(PARAMETER_EMAIL);
        String siteName = parameterMap.get(PARAMETER_SITE_NAME);

        List<WbxProvTask> tasks = wbxProvTaskRepository.findByTypeAndSiteNameAndHostEmailIgnoreCaseOrderByCreateTimeDesc(WbxProvTaskType.HOST.toString(),
                siteName, adminEmail);

        if(tasks.size()>0){
            bResult = true;
        }

        return bResult;
    }

    public List<String> getOrderIds(Map<String, String> parameterMap){
        List<String> orderIds = null;
        String adminEmail = parameterMap.get(PARAMETER_EMAIL);
        String siteName = parameterMap.get(PARAMETER_SITE_NAME);

        List<WbxProvTask> tasks = wbxProvTaskRepository.findByTypeAndSiteNameAndHostEmailIgnoreCaseOrderByCreateTimeDesc(WbxProvTaskType.HOST.toString(),
                siteName, adminEmail);

        if(tasks.size()>0){
            String orderId=tasks.get(0).getOrderId();
            String[] orderIdArray = orderId.split(",");
            orderIds = Arrays.asList(orderIdArray);
        }

        return orderIds;
    }

    private List<TelecomProductBean> findAllTelecomProduction() throws WafException {
        List<TelecomProductBean> beans = new ArrayList<TelecomProductBean>();

        List<AbstractChargeScheme> chargeSchemes = chargeSchemeService.findChargeScheme(CommonState.IN_EFFECT.toString(),
                ChargeType.TELECOM_CHARGE.toString(), true);
        for(AbstractChargeScheme chargeScheme: chargeSchemes){
            String chargeSchemeId = chargeScheme.getId();
            List<Product> products = productService.findByChargeSchemeId(chargeSchemeId);
            if(products!=null && products.size()>0){
                Product product = products.get(0);
                if(product!=null){
                    TelecomProductBean bean = new TelecomProductBean();
                    bean.setProductionId(product.getPid());
                    AbstractBizScheme bizScheme = bizSchemeService.findBizSchemeById(product.getBizId());
                    bean.setBizType(bizScheme.getType());
                    if(bizScheme instanceof BizWebExMC){
                        bean.setAttendeeCapacity(((BizWebExMC) bizScheme).getPorts());
                    }else{
                        //Currently telecom only supports MC
                    }
                    beans.add(bean);
                }
            }
        }

        return beans;
    }

    private boolean isProvAdminAccount(Map<String, String> parameterMap){
        String isAdminAccount=parameterMap.get(PARAMETER_IS_ADMIN_ACCOUNT);
        if("true".equals(isAdminAccount)){
            return true;
        }else{
            return false;
        }
    }

    private WebExSitePrimaryFields getWebExSitePrimaryFields(String siteName, boolean isPure010){
        WebExSitePrimaryFields wbxFields = new WebExSitePrimaryFields();

        wbxFields.setSiteName(siteName);
        if(isPure010){
            wbxFields.setLocation(Location.KETIAN_FREE);
        }else {
            wbxFields.setLocation(Location.KETIAN_CT);
        }
        LanguageMatrix languageMatrix = new LanguageMatrix();
        languageMatrix.setPrimaryLanguage(Language.SIMPLIFIED_CHINESE);
        languageMatrix.enable(Language.ENGLISH);
        languageMatrix.enable(Language.TRADITIONAL_CHINESE);
        wbxFields.setLanguages(languageMatrix);
        wbxFields.setTimeZone(TimeZone.BEIJING);

        return wbxFields;
    }
}
