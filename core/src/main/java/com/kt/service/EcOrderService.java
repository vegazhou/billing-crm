package com.kt.service;

import com.kt.biz.bean.ContractBean;
import com.kt.biz.bean.GenericChargeSchemeBean;
import com.kt.biz.bean.TelecomProductBean;
import com.kt.biz.customer.CustomerLevel;
import com.kt.biz.customer.CustomerOptionalFields;
import com.kt.biz.customer.CustomerPrimaryFields;
import com.kt.biz.model.biz.AbstractBizScheme;
import com.kt.biz.model.biz.impl.BizWebExMC;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayPersonal;
import com.kt.biz.model.common.SchemeKeys;
import com.kt.biz.site.LanguageMatrix;
import com.kt.biz.site.WebExSitePrimaryFields;
import com.kt.biz.types.*;
import com.kt.biz.types.TimeZone;
import com.kt.entity.mysql.batch.ProvHostConfig;
import com.kt.entity.mysql.batch.WbxProvTask;
import com.kt.entity.mysql.crm.*;
import com.kt.entity.mysql.user.OrgUser;
import com.kt.exception.*;
import com.kt.repo.mysql.batch.ChargeSchemeAttributeRepository;
import com.kt.repo.mysql.batch.ProvHostConfigRepository;
import com.kt.repo.mysql.batch.WbxProvTaskRepository;
import com.kt.service.billing.AccountService;
import com.kt.session.PrincipalContext;
import com.kt.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2016/6/3.
 */
@Service
public class EcOrderService {

    private static final Logger LOGGER = Logger.getLogger(EcOrderService.class);

    public final static String PARAMETER_ACTION = "action";
    public final static String PARAMETER_COMPANY_NAME = "companyName";
    public final static String PARAMETER_ADDRESS = "address";
    public final static String PARAMETER_BANK_NAME = "bankName";
    public final static String PARAMETER_BANK_CARD_NO = "bankCardNo";
    public final static String PARAMETER_CONTACT_PHONE = "contactPhone";
    public final static String PARAMETER_SALES_EMAIL = "salesEmail";
    public final static String PARAMETER_HOST_FN = "hostFirstName";
    public final static String PARAMETER_HOST_LN = "hostLastName";
    public final static String PARAMETER_HOST_EMAIL = "hostEmail";
    public final static String PARAMETER_ACTUAL_PAY_SUM = "actualPaySum";

    public final static String PARAMETER_MAINPROD_SERVICE_TYPE = "mainprod.serviceType";
    public final static String PARAMETER_MAINPROD_ATTENDEE_CAPACITY = "mainprod.attendeeCapacity";
    public final static String PARAMETER_MAINPROD_UNIT_PRICE = "mainprod.unitPrice";
    public final static String PARAMETER_MAINPROD_MONTH_NUM = "mainprod.monthNum";

    public final static String PARAMETER_PSTN_UNIT_PRICE = "pstn.unitPrice";
    public final static String PARAMETER_PSTN_MONTH_NUM = "pstn.monthNum";

    public final static String PARAMETER_PSTN_PACKET_UNIT_PRICE = "pstnpacket.unitPrice";
    public final static String PARAMETER_PSTN_PACKET_MINUTES_NUM = "pstnpacket.pstnPacketMinutesNum";
    public final static String PARAMETER_PSTN_PACKET_MONTH_NUM = "pstnpacket.monthNum";

    private final static String PARAMETER_SERVICE_TYPE = "ServiceType";
    private final static String PARAMETER_PAY_PERIOD = "PayPeriod";
    public final static String PARAMETER_TD_TYPE = "TelDomainType";
    public final static String PARAMETER_SITE_NAME = "siteName";

    public final static String PARAMETER_IS_ADMIN_ACCOUNT = "IsAdminAccount";
    public final static String PARAMETER_LICENSE_VOLUME = "LicenseVolume";
    public final static String PARAMETER_TELECOM_ADMIN_ORDERIDS = "OrderIds";
    public final static String PARAMETER_TELECOM_ADMIN_CONTRACTID = "ContractId";

    private final static String CONTRACT_PREFIX="EcommerceContract-";

    private final static String SITE_CONTRACT_PREFIX="EcommerceSiteContract-";

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

    @Autowired
    private AccountService accountService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createOrder(Map<String, String> parameterMap, String ecommerceCustomerId, String salesManId,
                            String ecommerceProdId, String pstnProdId, String pstnPacketProductId)
            throws WafException,OrderIncompleteException,OrderCollisionsDetectedException {

        virtualCreateSessionUser();
        if(this.isProvAdminAccount(parameterMap)){  //Provision site
            createSiteOrder(parameterMap, ecommerceCustomerId,salesManId, ecommerceProdId,pstnProdId);
        }else{  //Provision host
            createCIHostOrder(parameterMap, salesManId, ecommerceProdId, pstnProdId, pstnPacketProductId);
        }
        virtualRemoveSessionUser();
    }

    private void createSiteOrder(Map<String, String> parameterMap, String telecomCustomerId, String telecomSalesManId,
                            String telecomProdId, String pstnProdId)
            throws WafException,OrderIncompleteException,OrderCollisionsDetectedException {

        if(isSiteOrderExist(parameterMap)){
            throw new OrderExistException();
        }

        String email = parameterMap.get(PARAMETER_HOST_EMAIL);

        if (StringUtils.isEmpty(email)) {
            Date date = new Date();
            email = Long.toString(date.getTime());
        }
        Contract contract = contractService.draftTelecomContract(telecomCustomerId, SITE_CONTRACT_PREFIX + email, telecomSalesManId);
        LOGGER.info("Add ecommerce order step1 - add draft contact: contractId=" + contract.getPid());
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
            LOGGER.info("Add ecommerce order step2 - add draft site: siteId=" + siteDraft.getId());
        }

        String tempMonths = parameterMap.get(PARAMETER_MAINPROD_MONTH_NUM);
        int months = Integer.parseInt(tempMonths);
        Order mainProdOrder = orderService.placeTelecomOrder(contract.getPid(), telecomProdId,
                DateUtil.getSystemDate(), getPayInterval(parameterMap));
        LOGGER.info("Add ecommerce order step3 - add draft main-product order: orderId=" + mainProdOrder.getPid());
        chargeSchemeService.updateTelecomChargeScheme(mainProdOrder.getChargeId(), getGenericChargeSchemeBean4SiteMainProd(parameterMap));
        String orderIds="";
        if(mainProdOrder!=null){
            orderIds = mainProdOrder.getPid();
        }

        Order pstnOrder = orderService.placeTelecomOrder(contract.getPid(), pstnProdId,
                DateUtil.getSystemDate(), getPayInterval(parameterMap));
        LOGGER.info("Add ecommerce order step4 - add draft PSTN order: orderId=" + pstnOrder.getPid());
        chargeSchemeService.updateTelecomChargeScheme(pstnOrder.getChargeId(), getGenericChargeSchemeBean4SitePstnProd(parameterMap, mainProdOrder.getEffectiveEndDate()));
        if(pstnOrder!=null){
            if(orderIds.length()>0) {
                orderIds = orderIds + "," + pstnOrder.getPid();
            }else{
                orderIds = pstnOrder.getPid();
            }
        }
        parameterMap.put(PARAMETER_TELECOM_ADMIN_ORDERIDS,orderIds);

        LOGGER.info("Add ecommerce order step5 - update charge scheme info");
        contractService.sendTelecomContract4Approval(contract.getPid());
        LOGGER.info("Add ecommerce order step6 - send contract for approval");
        contractService.approveTelecom(contract.getPid());
        LOGGER.info("Add ecommerce order step7 - approve contract");
        contractService.finApproveTelecom(contract.getPid(),0f);
        LOGGER.info("Add ecommerce order successfully.");
    }

    private void createCIHostOrder(Map<String, String> parameterMap, String telecomSalesManId,
                                String ecommerceProdId, String pstnProdId, String pstnPacketProductId)
            throws WafException,OrderIncompleteException,OrderCollisionsDetectedException {
        //Todo: No handle multiple WebExConfMonthlyPayPersonal instances.
        WebExConfMonthlyPayPersonal ecommerceCharge = findChargeScheme(parameterMap);
        boolean isFirstBuy = parameterMap.containsKey(PARAMETER_MAINPROD_MONTH_NUM);
        Order existMainProdOrder=null;
        if(ecommerceCharge!=null){
            existMainProdOrder = orderService.findOrderByChargeId(ecommerceCharge.getId());
            if(existMainProdOrder.getState().equals(CommonState.END_OF_LIFE.toString())) {
                if(!isFirstBuy){
                    LOGGER.info("Main product MC3 is END_OF_LIFE");
                    throw new OrderNotInEffectException();
                }else{
                    throw new OrderExistException();
                }
            }else if(isFirstBuy){
                throw new OrderExistException();
            }
        }

        String contactName = parameterMap.get(PARAMETER_HOST_EMAIL) + "-" +
                DateUtil.formatInvoiceNameDate(DateUtil.getSystemDate());

        if (StringUtils.isEmpty(contactName)) {
            Date date = new Date();
            contactName = Long.toString(date.getTime());
        }

        if(isFirstBuy) {
            CustomerPrimaryFields primaryFields = generateCustomerPrimaryFields(parameterMap);
            CustomerOptionalFields optionalFields = generateCustomerOptionalFields(parameterMap);
            Customer customer = customerService.provisionCustomer(primaryFields, optionalFields, false);
            String ecommerceCustomerId = customer.getPid();
            LOGGER.info("Add ecommerce order step1 - add ecommerce customer: customerId=" + customer.getPid());

            //Contract contract = contractService.draftTelecomContract(ecommerceCustomerId, CONTRACT_PREFIX + fullName, telecomSalesManId);
            ContractBean contractBean = generageContractBean(ecommerceCustomerId, CONTRACT_PREFIX + contactName, telecomSalesManId);
            Contract contract = contractService.draftContract(contractBean);
            LOGGER.info("Add ecommerce order step2 - add draft contact: contractId=" + contract.getPid());

            Order mainProdOrder = orderService.placeOrder(contract.getPid(), ecommerceProdId,
                    DateUtil.getSystemDate(), getPayInterval(parameterMap));
            LOGGER.info("Add ecommerce order step3 - add draft main product order: orderId=" + mainProdOrder.getPid());
            chargeSchemeService.updateChargeScheme(mainProdOrder.getChargeId(), getGenericChargeSchemeBean4CIHostMainProd(parameterMap));

            Order pstnOrder = orderService.placeOrder(contract.getPid(), pstnProdId,
                    DateUtil.getSystemDate(), getPayInterval(parameterMap));
            LOGGER.info("Add ecommerce order step4 - add draft PSTN order: orderId=" + pstnOrder.getPid());
            chargeSchemeService.updateChargeScheme(pstnOrder.getChargeId(), getGenericChargeSchemeBean4PersonalPstnProd(parameterMap, mainProdOrder.getEffectiveEndDate()));

            Order pstnPacketOrder = orderService.placeOrder(contract.getPid(), pstnPacketProductId,
                    DateUtil.getSystemDate(), getPayInterval(parameterMap));
            LOGGER.info("Add ecommerce order step5 - add draft PSTN packet order: orderId=" + pstnPacketOrder.getPid());
            chargeSchemeService.updateChargeScheme(pstnPacketOrder.getChargeId(), getGenericChargeSchemeBean4PersonalPstnPacketProd(parameterMap, mainProdOrder.getEffectiveEndDate()));

            contractService.sendContract4Approval(contract.getPid(),false);
            LOGGER.info("Add ecommerce order step6 - send contract for approval");
            contractService.approve(contract.getPid(),false);
            LOGGER.info("Add ecommerce order step7 - approve contract");
            contractService.finApprove(contract.getPid(),Float.parseFloat(parameterMap.get(PARAMETER_ACTUAL_PAY_SUM)), 0f);
            LOGGER.info("Add ecommerce order successfully.");
        }else{
            String ecommerceCustomerId ="";
            if(existMainProdOrder!=null){
                ecommerceCustomerId=existMainProdOrder.getCustomerId();
            }
            ContractBean contractBean = generageContractBean(ecommerceCustomerId, CONTRACT_PREFIX + contactName, telecomSalesManId);
            Contract contract = contractService.draftContract(contractBean);
            LOGGER.info("Add ecommerce order step1 - add draft contact: contractId=" + contract.getPid());

            if(!isPstnOrderValid(ecommerceCustomerId,pstnProdId, existMainProdOrder.getEffectiveEndDate())) {
                Order pstnOrder = orderService.placeOrder(contract.getPid(), pstnProdId,
                        DateUtil.getSystemDate(), getPayInterval(parameterMap));
                LOGGER.info("Add ecommerce order step2 - add draft PSTN order: orderId=" + pstnOrder.getPid());
                chargeSchemeService.updateChargeScheme(pstnOrder.getChargeId(),
                        getGenericChargeSchemeBean4PersonalPstnProd(parameterMap, existMainProdOrder.getEffectiveEndDate()));
            }

            Order pstnPacketOrder = orderService.placeOrder(contract.getPid(), pstnPacketProductId,
                    DateUtil.getSystemDate(), getPayInterval(parameterMap));
            LOGGER.info("Add ecommerce order step3 - add draft PSTN packet order: orderId=" + pstnPacketOrder.getPid());
            chargeSchemeService.updateChargeScheme(pstnPacketOrder.getChargeId(), getGenericChargeSchemeBean4PersonalPstnPacketProd(parameterMap, existMainProdOrder.getEffectiveEndDate()));

            contractService.sendContract4Approval(contract.getPid(),false);
            LOGGER.info("Add ecommerce order step4 - send contract for approval");
            contractService.approve(contract.getPid(),false);
            LOGGER.info("Add ecommerce order step5 - approve contract");
            contractService.finApprove(contract.getPid(),Float.parseFloat(parameterMap.get(PARAMETER_ACTUAL_PAY_SUM)), 0f);
            LOGGER.info("Add ecommerce order successfully.");
        }
    }

    private boolean isPstnOrderValid(String customerId, String pstnProdId, String mainProductEndDate){
        boolean result=false;
        List<Order> orders = this.orderService.findOrderByStateAndCustomerAndProductIdOrderByPlacedDate(CommonState.IN_EFFECT.toString(),
                customerId,pstnProdId);
        if(orders!=null && orders.size()>0){
            result=true;
        }
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    //Return ChargeSchemeId
    public String updateOrder(Map<String, String> parameterMap)
            throws WafException{
        WebExConfMonthlyPayPersonal ecommerceCharge = findChargeScheme(parameterMap);
        if(ecommerceCharge==null){
            LOGGER.info("Update telecom order failed!");
            throw new ChargeSchemeNotFoundException();
        }else{
            checkOrderState(ecommerceCharge.getId());
        }
        String displayName = parameterMap.get(PARAMETER_HOST_LN) + parameterMap.get(PARAMETER_HOST_FN);
        if(displayName!=null && displayName.length()>0) {
            ecommerceCharge.setUserName(displayName);
        }
        chargeSchemeService.saveChargeScheme(ecommerceCharge);
        LOGGER.info("Update ecommerce order successfully: email=" + ecommerceCharge.getDisplayName() +
                ", siteName=" + ecommerceCharge.getSiteName());
        return ecommerceCharge.getId();
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
        WebExConfMonthlyPayPersonal ecommerceCharge = findChargeScheme(parameterMap);
        if(ecommerceCharge==null){
            LOGGER.info("Terminate ecommerce order failed!");
            throw new ChargeSchemeNotFoundException();
        }else{
            checkOrderState(ecommerceCharge.getId());
        }
        Order order = orderService.findOrderByChargeId(ecommerceCharge.getId());
        orderService.terminateTelecomOrderOnSpecificDay(order.getPid(),DateUtil.getSystemDate());
        LOGGER.info("Terminate ecommerce order successfully: orderId=" + order.getPid());
    }

    public void terminateSiteOrder(Map<String, String> parameterMap) throws WafException{

        if(!isSiteOrderExist(parameterMap)){
            LOGGER.info("Terminate ecommerce order failed!");
            throw new ChargeSchemeNotFoundException();
        }
        List<String> orderIds = getOrderIds(parameterMap);
        for(String orderId: orderIds) {
            orderService.terminateTelecomOrderOnSpecificDay(orderId, DateUtil.getSystemDate());
            LOGGER.info("Terminate ecommerce order successfully: orderId=" + orderId);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void feePay(String customerId, BigDecimal mount) {
        virtualCreateSessionUser();
        accountService.deposit(customerId, AccountType.DEPOSIT, mount);
        virtualRemoveSessionUser();
    }

    public String getEcommerceCustomerId(Map<String, String> parameterMap){
        String customerId=null;
        WebExConfMonthlyPayPersonal ecommerceCharge = findChargeScheme(parameterMap);
        Order existMainProdOrder=null;
        if(ecommerceCharge!=null){
            try {
                existMainProdOrder = orderService.findOrderByChargeId(ecommerceCharge.getId());
                customerId = existMainProdOrder.getCustomerId();
            } catch (OrderNotFoundException e) {
                LOGGER.info("Ecommerce main product order not found by chargeId: " + e.getKey());
            }
        }
        return customerId;
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
        bean.setMonthAmount(Integer.parseInt(parameterMap.get(PARAMETER_MAINPROD_MONTH_NUM)));
        bean.setUnitPrice(Float.parseFloat(parameterMap.get(PARAMETER_MAINPROD_UNIT_PRICE)));
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

    public WebExConfMonthlyPayPersonal findChargeScheme(Map<String, String> parameterMap){
        String webexId = parameterMap.get(PARAMETER_HOST_EMAIL);
        String configSiteName = parameterMap.get(PARAMETER_SITE_NAME);
        List<ChargeSchemeAttribute> chargeAttributes = chargeSchemeAttributeRepository.findByNameAndValueIgnoreCase(SchemeKeys.DISPLAY_NAME, webexId);
        for(ChargeSchemeAttribute chargeAttribute:chargeAttributes){
            String id = chargeAttribute.getEntityId();
            try {
                AbstractChargeScheme chargeScheme = chargeSchemeService.findChargeSchemeById(id);
                if(chargeScheme instanceof WebExConfMonthlyPayPersonal){
                    String siteName = ((WebExConfMonthlyPayPersonal) chargeScheme).getSiteName();
                    if(siteName.toLowerCase().equals(configSiteName.toLowerCase())){
                        return ((WebExConfMonthlyPayPersonal) chargeScheme);
                    }
                }else{//异常电信工单
                    LOGGER.info("Abnormal Ecommerce order: id=" + id);
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
        String adminEmail = parameterMap.get(PARAMETER_HOST_EMAIL);
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
                    LOGGER.info("Create Ecommerce site order: validate site order exist failed");
                }
            }
        }

        return bResult;
    }

    public boolean isRenewOrder(Map<String, String> parameterMap){
        boolean bResult = false;
        String adminEmail = parameterMap.get(PARAMETER_HOST_EMAIL);
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
        String adminEmail = parameterMap.get(PARAMETER_HOST_EMAIL);
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

    //type: 0: all products; 1: main product; 2: pstn product
    public List<String> findProvUserConfigByProductType(String productType, int type)
    {
        List<String> productIds=new ArrayList<String>();
        List<ProvHostConfig> configs = provHostConfigRepository.findByProductType(productType);
        for(ProvHostConfig cfg: configs){
            if (type == 0) {
                if (cfg.getProductId() != null && cfg.getProductId().length() > 0) {
                    productIds.add(cfg.getProductId());
                }
                if (cfg.getPstnProdId() != null && cfg.getPstnProdId().length() > 0) {
                    productIds.add(cfg.getPstnProdId());
                }
            }else if (type == 1) {
                if (cfg.getProductId() != null && cfg.getProductId().length() > 0) {
                    productIds.add(cfg.getProductId());
                }
            }else if (type == 2) {
                if (cfg.getPstnProdId() != null && cfg.getPstnProdId().length() > 0) {
                    productIds.add(cfg.getPstnProdId());
                }
            }
        }
        return productIds;
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
            wbxFields.setLocation(Location.KETIAN_FREE_T30);
        }else {
            wbxFields.setLocation(Location.KETIAN_CT_T30);
        }
        LanguageMatrix languageMatrix = new LanguageMatrix();
        languageMatrix.setPrimaryLanguage(Language.SIMPLIFIED_CHINESE);
        languageMatrix.enable(Language.ENGLISH);
        languageMatrix.enable(Language.TRADITIONAL_CHINESE);
        wbxFields.setLanguages(languageMatrix);
        wbxFields.setTimeZone(TimeZone.BEIJING);

        return wbxFields;
    }

    private void virtualCreateSessionUser(){
        OrgUser user = new OrgUser();
        user.setFullName("Wbxprov Bss");
        user.setUserName("wbxprov.bss@ketianyun.com");
        user.setRoleId(RoleType.SUPER_ADMIN.toString());
        PrincipalContext.storePrincipal(user);
    }

    private void virtualRemoveSessionUser(){
        PrincipalContext.clearPrincipal();
    }

    private CustomerPrimaryFields generateCustomerPrimaryFields(Map<String, String> parameterMap){
        CustomerPrimaryFields fields = new CustomerPrimaryFields();
        fields.setAgentType(AgentType.DIRECT);
        fields.setContactEmail(parameterMap.get(PARAMETER_HOST_EMAIL));
        fields.setContactName(parameterMap.get(PARAMETER_HOST_LN) + parameterMap.get(PARAMETER_HOST_FN));
        fields.setContactPhone(parameterMap.get(PARAMETER_CONTACT_PHONE));
        fields.setLevel(CustomerLevel.FIVE);
        fields.setIsVat(false);//普通发票
        fields.setDisplayName(parameterMap.get(PARAMETER_COMPANY_NAME));
        return fields;
    }

    private CustomerOptionalFields generateCustomerOptionalFields(Map<String, String> parameterMap){
        CustomerOptionalFields fields = new CustomerOptionalFields();
        if(parameterMap.containsKey(PARAMETER_ADDRESS)){
            fields.setAddress(parameterMap.get(PARAMETER_ADDRESS));
        }
        if(parameterMap.containsKey(PARAMETER_BANK_NAME)){
            fields.setBank(parameterMap.get(PARAMETER_BANK_NAME));
        }
        if(parameterMap.containsKey(PARAMETER_BANK_CARD_NO)){
            fields.setBankAccount(parameterMap.get(PARAMETER_BANK_CARD_NO));
        }
        if(parameterMap.containsKey(PARAMETER_CONTACT_PHONE)){
            fields.setPhone(parameterMap.get(PARAMETER_CONTACT_PHONE));
        }
        fields.setRel(false);
        return fields;
    }

    private ContractBean generageContractBean(String customerId, String contractName, String salesManId){
        ContractBean bean = new ContractBean();
        bean.setDisplayName(contractName);
        bean.setCustomerId(customerId);
        bean.setSalesmanId(salesManId);
        bean.setIsRegistered(false);
        return bean;
    }

    private GenericChargeSchemeBean getGenericChargeSchemeBean4CIHostMainProd(Map<String, String> parameterMap){
        GenericChargeSchemeBean bean = new GenericChargeSchemeBean();
//        bean.setCommonSite(parameterMap.get(PARAMETER_SITE_NAME));
        bean.setMonthAmount(Integer.parseInt(parameterMap.get(PARAMETER_MAINPROD_MONTH_NUM)));
        bean.setUnitPrice(Float.parseFloat(parameterMap.get(PARAMETER_MAINPROD_UNIT_PRICE)));
        bean.setDisplayName(parameterMap.get(PARAMETER_HOST_EMAIL));
        return bean;
    }

    private GenericChargeSchemeBean getGenericChargeSchemeBean4PersonalPstnProd(Map<String, String> parameterMap, String endDate){
        GenericChargeSchemeBean bean = new GenericChargeSchemeBean();
//        bean.setCommonSite(parameterMap.get(PARAMETER_SITE_NAME));
        bean.setEffectiveBefore(endDate);
        bean.setDisplayName(parameterMap.get(PARAMETER_HOST_EMAIL));
        return bean;
    }

    private GenericChargeSchemeBean getGenericChargeSchemeBean4PersonalPstnPacketProd(Map<String, String> parameterMap, String endDate){
        GenericChargeSchemeBean bean = new GenericChargeSchemeBean();
//        bean.setCommonSite(parameterMap.get(PARAMETER_SITE_NAME));
        bean.setEffectiveBefore(endDate);
        bean.setTotalPrice(Float.parseFloat(parameterMap.get(PARAMETER_PSTN_PACKET_UNIT_PRICE)));
        bean.setPackageMinutes(Integer.parseInt(parameterMap.get(PARAMETER_PSTN_PACKET_MINUTES_NUM)));
        bean.setDisplayName(parameterMap.get(PARAMETER_HOST_EMAIL));
        return bean;
    }

}
