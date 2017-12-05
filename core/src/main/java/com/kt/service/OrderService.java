package com.kt.service;

import com.kt.biz.auth.PrivilegeChecker;
import com.kt.biz.bean.OrderTableRow;
import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.model.biz.AbstractBizScheme;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.order.*;
import com.kt.biz.types.CommonState;

import com.kt.biz.types.OrderProvisionStatus;
import com.kt.biz.types.PayInterval;

import com.kt.entity.mysql.crm.Contract;
import com.kt.entity.mysql.crm.Order;
import com.kt.entity.mysql.crm.Product;
import com.kt.exception.*;
import com.kt.repo.mysql.batch.OrderRepository;
import com.kt.repo.mysql.batch.WebExSiteDraftOrderRelationRepository;
import com.kt.repo.mysql.billing.BillFormalDetailRepository;
import com.kt.util.DateUtil;
import com.kt.util.MathUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.*;


/**
 * Created by Vega Zhou on 2016/3/7.
 */
@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ContractService contractService;
    @Autowired
    ProductService productService;
    @Autowired
    BizSchemeService bizSchemeService;
    @Autowired
    ChargeSchemeService chargeSchemeService;
    @Autowired
    WebExSiteDraftOrderRelationRepository webExSiteDraftOrderRelationRepository;
    @Autowired
    BillFormalDetailRepository billFormalDetailRepository;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<Order> placeOrders(String contractId, List<String> productIds, Date startDate, PayInterval payInterval, String bizChance) throws WafException {
        List<Order> newOrders = new LinkedList<>();
        for (String productId : productIds) {
            Order order = placeOrder(contractId, productId, startDate, payInterval, bizChance);
            newOrders.add(order);
        }
        return newOrders;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Order placeOrder(String contractId, String productId, Date startDate, PayInterval payInterval) throws WafException {
        return placeOrder(contractId, productId, startDate, payInterval, null);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private Order placeOrder(String contractId, String productId, Date startDate, PayInterval payInterval, String bizChance) throws WafException {
        if (startDate.before(DateUtil.beginOfToday())) {
            throw new InvalidOrderStartDateException();
        }
        return placeOrderIgnoreInvalidStartDate(contractId, productId, startDate, payInterval, bizChance);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Order placeOrderIgnoreInvalidStartDate(String contractId, String productId, Date startDate, PayInterval payInterval, String bizChance) throws WafException {
        PrivilegeChecker.isOperator();
        Contract contract = contractService.findByContractId(contractId);
        Product product = productService.findById(productId);
        startDate = DateUtils.truncate(startDate, Calendar.DATE);

        if (CommonState.DRAFT != CommonState.valueOf(contract.getState())) {
            throw new OnlyDraftingContractAllowedException();
        }
        if (CommonState.IN_EFFECT != CommonState.valueOf(product.getState())) {
            throw new OnlyInEffectiveProductAllowedException();
        }

        AbstractBizScheme bizTemplate = bizSchemeService.findBizSchemeById(product.getBizId());
        AbstractBizScheme bizScheme = bizSchemeService.instantiateFromTemplate(bizTemplate);
        bizSchemeService.saveBizScheme(bizScheme);

        AbstractChargeScheme chargeTemplate = chargeSchemeService.findChargeSchemeById(product.getChargeSchemeId());
        AbstractChargeScheme chargeScheme = chargeSchemeService.instantiateFromTemplate(chargeTemplate);
        chargeSchemeService.saveChargeScheme(chargeScheme);

        Order order = new Order();
        order.setEffectiveStartDate(DateUtil.formatDate(startDate));
        order.setContractId(contract.getPid());
        order.setProductId(product.getPid());
        order.setState(CommonState.DRAFT.toString());
        order.setBizId(bizScheme.getId());
        order.setPayInterval(payInterval.getInterval());
        order.setChargeId(chargeScheme.getId());
        Date endDate = chargeScheme.calculateEndDate(startDate);
        order.setEffectiveEndDate(DateUtil.formatDate(endDate));
        double firstInstallment = chargeScheme.calculateFirstInstallment(startDate, payInterval);
        order.setFirstInstallment(MathUtil.scale(firstInstallment));
        order.setFiType(chargeScheme.getType().getFirstInstallmentFeeType().getValue());
        order.setSysFirstInstallment(MathUtil.scale(firstInstallment));
        order.setTotalAmount(chargeScheme.calculateTotalAmount(startDate, endDate));
        order.setCustomerId(contract.getCustomerId());
        order.setBizChance(bizChance);


        return orderRepository.save(order);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Order placeTelecomOrder(String contractId, String productId, Date startDate, PayInterval payInterval) throws WafException {
        Contract contract = contractService.findByContractId(contractId);
        Product product = productService.findById(productId);
        startDate = DateUtils.truncate(startDate, Calendar.DATE);

        if (CommonState.DRAFT != CommonState.valueOf(contract.getState())) {
            throw new OnlyDraftingContractAllowedException();
        }
        if (CommonState.IN_EFFECT != CommonState.valueOf(product.getState())) {
            throw new OnlyInEffectiveProductAllowedException();
        }
        //API don't check startDate
//        if (startDate.before(new Date())) {
//            throw new InvalidOrderStartDateException();
//        }

        AbstractBizScheme bizTemplate = bizSchemeService.findBizSchemeById(product.getBizId());
        AbstractBizScheme bizScheme = bizSchemeService.instantiateFromTemplate(bizTemplate);
        bizSchemeService.saveBizScheme(bizScheme);

        AbstractChargeScheme chargeTemplate = chargeSchemeService.findChargeSchemeById(product.getChargeSchemeId());
        AbstractChargeScheme chargeScheme = chargeSchemeService.instantiateFromTemplate(chargeTemplate);
//        if(chargeScheme instanceof TelecomCharge){
//            ((TelecomCharge)chargeScheme).setSiteName(telecomBean.getSiteName());
//            ((TelecomCharge)chargeScheme).setEnterpriseCode(telecomBean.getEnterpriseCode());
//            ((TelecomCharge)chargeScheme).setEnterpriseName(telecomBean.getEnterpriseName());
//            ((TelecomCharge)chargeScheme).setInitialPassword(telecomBean.getInitialPassword());
//            ((TelecomCharge)chargeScheme).setUserName(telecomBean.getUserName());
//            ((TelecomCharge)chargeScheme).setWebexId(telecomBean.getWebexId());
//        }
        chargeSchemeService.saveChargeScheme(chargeScheme);

        Order order = new Order();
        order.setEffectiveStartDate(DateUtil.formatDate(startDate));
        order.setContractId(contract.getPid());
        order.setProductId(product.getPid());
        order.setState(CommonState.DRAFT.toString());
        order.setBizId(bizScheme.getId());
        order.setPayInterval(payInterval.getInterval());
        order.setChargeId(chargeScheme.getId());
        Date endDate = chargeScheme.calculateEndDate(startDate);
        order.setEffectiveEndDate(DateUtil.formatDate(endDate));
        double firstInstallment = chargeScheme.calculateFirstInstallment(startDate, payInterval);
        order.setFirstInstallment(MathUtil.scale(firstInstallment));
        order.setSysFirstInstallment(MathUtil.scale(firstInstallment));
        order.setFiType(chargeScheme.getType().getFirstInstallmentFeeType().getValue());
        order.setTotalAmount(chargeScheme.calculateTotalAmount(startDate, endDate));
        order.setCustomerId(contract.getCustomerId());


        return orderRepository.save(order);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void cloneContractOrders4Alteration(String contractId, String alterationDraftContractId) throws WafException {
        Contract contract = contractService.findByContractId(contractId);
        CommonState state = CommonState.valueOf(contract.getState());
        if (state != CommonState.IN_EFFECT) {
            throw new OnlyInEffectContractAllowedException();
        }

        List<Order> originalOrders = findOrdersByContractId(contractId);
        for (Order originalOrder : originalOrders) {
            cloneOrder4Alteration(originalOrder, alterationDraftContractId);
        }
    }

    private void cloneOrder4Alteration(Order originalOrder, String targetContractId) {
        Order cloned = new Order();
        cloned.setBizId(originalOrder.getBizId());
        cloned.setChargeId(originalOrder.getChargeId());
        cloned.setProductId(originalOrder.getProductId());
        cloned.setContractId(targetContractId);
        cloned.setEffectiveStartDate(originalOrder.getEffectiveStartDate());
        cloned.setEffectiveEndDate(originalOrder.getEffectiveEndDate());
        cloned.setPayInterval(originalOrder.getPayInterval());
        cloned.setState(CommonState.DRAFT.toString());
        cloned.setOriginalOrderId(originalOrder.getPid());
        cloned.setFirstInstallment(0d);
        cloned.setSysFirstInstallment(MathUtil.scale(originalOrder.getSysFirstInstallment()));
        cloned.setFiType(originalOrder.getFiType());
        cloned.setTotalAmount(originalOrder.getTotalAmount());
        cloned.setCustomerId(originalOrder.getCustomerId());
        orderRepository.save(cloned);
    }


    /**
     * 将变更后的订单回写到原合同中
     * @param alterationOrderId 变更订单的草稿ID
     * @throws WafException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    protected void writeBackToOriginalContract(String alterationOrderId, String originalContractId) throws WafException {
        Order order = findOrderById(alterationOrderId);
        String originalOrderId = order.getOriginalOrderId();
        if (StringUtils.isBlank(originalOrderId)) {
            // 将新增的订单回写到原合同中
            order.setContractId(originalContractId);
            orderRepository.save(order);
            approve(alterationOrderId);
        } else {
            //将变更过的订单更新原订单
            Order originalOrder = findOrderById(originalOrderId);
            originalOrder.setTotalAmount(order.getTotalAmount());
            originalOrder.setEffectiveEndDate(order.getEffectiveEndDate());
            orderRepository.save(originalOrder);
            //将变更订单草稿删除
            orderRepository.delete(alterationOrderId);
            OrderBeanCache.expire(originalOrderId);
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void terminateOrderOnAccountPeriod(String orderId, AccountPeriod accountPeriod) throws WafException {
        OrderBean order = findOrderBeanById(orderId);
        terminateOrderOnSpecificDay(orderId, DateUtil.getSameDayOfMonth(order.getStartDate(), accountPeriod.getDate()));
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void terminateOrderOnSpecificDay(String orderId, Date date) throws WafException {
        OrderBean order = findOrderBeanById(orderId);
        OrderBean originalOrder = findOrderBeanById(order.getOriginalOrderId());
        assertOrderCanBeTerminated(order, originalOrder, date);
        _doTerminateOrder(orderId, date);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void terminateTelecomOrderOnSpecificDay(String orderId, Date date) throws WafException {
        //OrderBean order = findOrderBeanById(orderId);
        //OrderBean originalOrder = findOrderBeanById(order.getOriginalOrderId());
        //assertOrderCanBeTerminated(order, originalOrder, date);
        _doTerminateOrder(orderId, date);
    }

    /**
     * 将订单提前中止，本函数不负责校验截止日期的正确性，
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void _doTerminateOrder(String orderId, Date endDate) throws WafException {
        try {
            Order order = orderRepository.findOne(orderId);
            Date startDate = DateUtil.toDate(order.getEffectiveStartDate());
            Date originalEndDate = DateUtil.toDate(order.getEffectiveEndDate());
            order.setEffectiveEndDate(DateUtil.formatDate(endDate));

            AbstractChargeScheme chargeScheme = chargeSchemeService.findChargeSchemeById(order.getChargeId());
            double totalAmount = chargeScheme.calculateTotalAmount(startDate, endDate);
            order.setTotalAmount(totalAmount);
            orderRepository.save(order);
            OrderBeanCache.expire(orderId);
        } catch (ParseException e) {
            throw new UnknownSystemError(e);
        }
    }


    public Order findOrderById(String id) throws OrderNotFoundException {
        Order order = orderRepository.findOne(id);
        if (order == null) {
            throw new OrderNotFoundException();
        }
        return order;
    }

    public OrderBean findOrderBeanById(String id) throws WafException {
        Order order = orderRepository.findOne(id);
        if (order == null) {
            throw new OrderNotFoundException();
        }
        return OrderBeanCache.get(order);
    }


    public Order findOrderByChargeId(String chargeId) throws OrderNotFoundException {
        Order order = orderRepository.findOneByChargeId(chargeId);
        if (order == null) {
            throw new OrderNotFoundException();
        }
        return order;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteOrderById(String orderId) throws WafException {
        PrivilegeChecker.isOperator();
        Order order = findOrderById(orderId);

        assertOrderCanBeDeleted(order);

        bizSchemeService.deleteBizScheme(order.getBizId());
        chargeSchemeService.deleteChargeScheme(order.getChargeId());

        orderRepository.delete(order);
        //also delete the site/order relationship
        webExSiteDraftOrderRelationRepository.deleteByOrderId(orderId);
    }


    /**
     * 警告: 会强制删除订单，仅供方便测试时使用
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteByForce(String orderId) throws WafException {
        Order order = orderRepository.findOne(orderId);
        if (StringUtils.isBlank(order.getOriginalOrderId())) {
            bizSchemeService.deleteByForce(order.getBizId());
            chargeSchemeService.deleteByForce(order.getChargeId());
        }

        orderRepository.delete(order);
        billFormalDetailRepository.deleteByOrderId(orderId);
        //also delete the site/order relationship
        webExSiteDraftOrderRelationRepository.deleteByOrderId(orderId);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void tuneFirstInstallment(String orderId, BigDecimal newAmount) throws WafException {
        Order order = findOrderById(orderId);
        assertOrderCanBeUpdated(order);
        assertFirstInstallmentNotZero(order);
        assertAmountGreaterThanZero(newAmount);
        BigDecimal gap = newAmount.subtract(new BigDecimal(order.getFirstInstallment()));
        if (gap.abs().compareTo(new BigDecimal(1)) >= 0) {
            throw new InvalidTuneOperationException();
        }
        order.setFirstInstallment(MathUtil.scale(newAmount.doubleValue()));
        BigDecimal newTotal = MathUtil.scale(new BigDecimal(order.getTotalAmount()).add(gap));
        order.setTotalAmount(newTotal.doubleValue());
        orderRepository.save(order);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateOrderById(String orderId, Date newStartDate, PayInterval payInterval, String bizChance) throws WafException, ParseException {
        PrivilegeChecker.isOperator();
        newStartDate = DateUtils.truncate(newStartDate, Calendar.DATE);

        Order order = findOrderById(orderId);
        if (order == null) {
            throw new OrderNotFoundException();
        }
        assertOrderCanBeUpdated(order);
        order.setBizChance(bizChance);
        if (newStartDate.before(DateUtil.beginOfToday())) {
            throw new InvalidOrderStartDateException();
        }
        Date oldStartDate = DateUtil.toDate(order.getEffectiveStartDate());
        AbstractChargeScheme chargeScheme = chargeSchemeService.findChargeSchemeById(order.getChargeId());
        Date newEndDate = chargeScheme.calculateEndDate(newStartDate);
        order.setEffectiveStartDate(DateUtil.formatDate(newStartDate));
        order.setEffectiveEndDate(DateUtil.formatDate(newEndDate));

        PayInterval originalPayInterval = new PayInterval(order.getPayInterval());
        if ((!originalPayInterval.equals(payInterval)) || newStartDate.compareTo(oldStartDate) != 0) {
            order.setPayInterval(payInterval.getInterval());
            double firstInstallment = chargeScheme.calculateFirstInstallment(newStartDate, payInterval);
            order.setFirstInstallment(MathUtil.scale(firstInstallment));
            order.setSysFirstInstallment(MathUtil.scale(firstInstallment));
            order.setTotalAmount(chargeScheme.calculateTotalAmount(newStartDate, newEndDate));
        }
        orderRepository.save(order);
        OrderBeanCache.expire(order.getPid());
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    protected void sendOrder4Approval(String orderId) throws WafException {
        PrivilegeChecker.isOperator();
        Order order = findOrderById(orderId);
        CommonState currentState = CommonState.valueOf(order.getState());
        if (currentState == CommonState.IN_EFFECT) {
            return;
        }
        if (currentState != CommonState.DRAFT) {
            throw new OnlyDraftingOrderAllowedException();
        }
        order.setState(CommonState.WAITING_APPROVAL.toString());
        orderRepository.save(order);

        bizSchemeService.sendBizScheme4Approval(order.getBizId());
        chargeSchemeService.sendChargeScheme4Approval(order.getChargeId());
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    protected void sendTelecomOrder4Approval(String orderId) throws WafException {
        Order order = findOrderById(orderId);
        CommonState currentState = CommonState.valueOf(order.getState());
        if (currentState == CommonState.IN_EFFECT) {
            return;
        }
        if (currentState != CommonState.DRAFT) {
            throw new OnlyDraftingOrderAllowedException();
        }
        order.setState(CommonState.WAITING_APPROVAL.toString());
        orderRepository.save(order);

        bizSchemeService.sendTelecomBizScheme4Approval(order.getBizId());
        chargeSchemeService.sendTelecomChargeScheme4Approval(order.getChargeId());
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    protected void withdraw(String orderId) throws WafException {
        PrivilegeChecker.isOperator();
        Order order = findOrderById(orderId);
        CommonState currentState = CommonState.valueOf(order.getState());
        if (currentState == CommonState.IN_EFFECT) {
            return;
        }
        if (currentState != CommonState.WAITING_APPROVAL) {
            throw new OnlyWaitingApprovalOrderAllowedException();
        }
        order.setState(CommonState.DRAFT.toString());
        orderRepository.save(order);

        bizSchemeService.withdraw(order.getBizId());
        chargeSchemeService.withdraw(order.getChargeId());
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    protected void decline(String orderId) throws WafException {
        Order order = findOrderById(orderId);
        CommonState currentState = CommonState.valueOf(order.getState());
        if (currentState == CommonState.IN_EFFECT) {
            return;
        }
        order.setState(CommonState.DRAFT.toString());
        orderRepository.save(order);
        OrderBeanCache.expire(orderId);

        bizSchemeService.decline(order.getBizId());
        chargeSchemeService.decline(order.getChargeId());
    }

    protected void approve(String orderId) throws WafException {
        Order order = findOrderById(orderId);
        CommonState currentState = CommonState.valueOf(order.getState());
        if (currentState == CommonState.IN_EFFECT) {
            return;
        }
        if (currentState != CommonState.WAITING_APPROVAL) {
            throw new OnlyWaitingApprovalOrderAllowedException();
        }
        order.setState(CommonState.IN_EFFECT.toString());
        order.setPlacedDate(DateUtil.now());
        orderRepository.save(order);

        bizSchemeService.approve(order.getBizId());
        chargeSchemeService.approve(order.getChargeId());
    }

    protected void approveTelecom(String orderId) throws WafException {
        Order order = findOrderById(orderId);
        CommonState currentState = CommonState.valueOf(order.getState());
        if (currentState == CommonState.IN_EFFECT) {
            return;
        }
        if (currentState != CommonState.WAITING_APPROVAL) {
            throw new OnlyWaitingApprovalOrderAllowedException();
        }
        order.setState(CommonState.IN_EFFECT.toString());
        order.setPlacedDate(DateUtil.now());
        orderRepository.save(order);

        bizSchemeService.approveTelecom(order.getBizId());
        chargeSchemeService.approveTelecom(order.getChargeId());
    }

    public List<OrderBean> findOrderContext(String contractId) throws WafException {
        List<Order> orders = orderRepository.findOrderContext(contractId);
        return convert2beans(orders);
    }

    public List<Order> findOrdersByContractId(String contractId) {
        return orderRepository.findByContractId(contractId);
    }


    public List<OrderBean> findOrderBeansByContractId(String contractId) throws WafException {
        List<Order> orders = orderRepository.findByContractId(contractId);
        return convert2beans(orders);
    }


    public List<Order> findOrderByStateAndDateOrderByContractId(String orderState, String date) {
        return orderRepository.findOrderByStateAndDateOrderByContractId(orderState, date);
    }

    public List<Order> findOrderByStateAndDateOrderByPlacedDate(String orderState, String date) {
        return orderRepository.findOrderByStateAndDateOrderByPlacedDate(orderState, date);
    }

    public List<Order> findByEffectiveEndDateOrderByCustomerId(String orderState, String date) {
        return orderRepository.findByStateAndEffectiveEndDateOrderByCustomerIdDesc(orderState, date);
    }

    public List<Order> findOrderByStateAndDateAndProductIdOrderByCustomerId(String orderState, String date, String productId) {
        return orderRepository.findOrderByStateAndDateAndProductIdOrderByCustomerId(orderState, date, productId);
    }

    public List<Order> findOrderByStateAndCustomerAndProductIdOrderByPlacedDate(String orderState, String customerId, String productId) {
        return orderRepository.findOrderByStateAndCustomerIdAndProductIdOrderByPlacedDate(orderState, customerId, productId);
    }

    public List<Order> findOrderByStateAndCustomerAndProductIdOrderByPlacedDate(String orderState, String customerId, List<String> productId) {
        return orderRepository.findByStateAndCustomerIdAndProductIdInOrderByPlacedDateDesc(orderState, customerId, productId);
    }

    public List<Order> findOrderByStateAndCustomerAndProductIdOrderByHost(String orderState, String date, List<String> productId) {
        return orderRepository.findOrderByStateAndDateAndProductIdOrderByHost(orderState, date, productId);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateOrderProvisionStatus(String orderId, OrderProvisionStatus opStatus, String curDate) {
        Order order = orderRepository.findOne(orderId);
        String orderEffectiveStartDate = order.getEffectiveStartDate();
        String orderEffectiveEndDate = order.getEffectiveEndDate();
        if (curDate == null) {
            order.setProvisionState(opStatus.toString());
        } else if (orderEffectiveStartDate.startsWith(curDate)) {
            if (orderEffectiveEndDate.startsWith(curDate) && OrderProvisionStatus.SUCCESS.toString().equals(order.getProvisionState())) {
                order.setTerminateState(opStatus.toString());
                order.setState(CommonState.END_OF_LIFE.toString());
            } else {
                order.setProvisionState(opStatus.toString());
            }
        } else if (orderEffectiveEndDate.startsWith(curDate)) {
            order.setTerminateState(opStatus.toString());
            if (opStatus.equals(OrderProvisionStatus.SUCCESS)) {
                order.setState(CommonState.END_OF_LIFE.toString());
            }
        }
        orderRepository.save(order);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateOrderProvisionStatus(String orderId, OrderProvisionStatus opStatus, boolean isTerminateOrder) {
        Order order = orderRepository.findOne(orderId);
        if (isTerminateOrder) {
            order.setTerminateState(opStatus.toString());
            if (opStatus.equals(OrderProvisionStatus.SUCCESS)) {
                order.setState(CommonState.END_OF_LIFE.toString());
            }
        } else {
            order.setProvisionState(opStatus.toString());
        }
        orderRepository.save(order);
    }

    public List<Order> listAllByPage(int curPage, SearchFilter search) {
        return orderRepository.listAllByPage(curPage, search);
    }

    public List<OrderTableRow> paginateOrders(int curPage, SearchFilter search) throws WafException {
        List<Order> orders = orderRepository.listAllByPage(curPage, search);
        if (orders == null) {
            return new ArrayList<>();
        }
        List<OrderTableRow> rows = new ArrayList<>(orders.size());

        for (Order order : orders) {
            rows.add(fromOrder2TableRow(order, true));
        }
//        Page<OrderTableRow> results = new Page<>(page.getPageSize(), rows, page.getCurPage(), page.getPageCount(), page.getRecordCount());
        return rows;
    }

    public List<OrderTableRow> paginateOrdersForReport(int curPage, SearchFilter search) throws WafException {
        List<Order> orders = orderRepository.listAllByPageForReport(curPage, search);
        List<OrderTableRow> rows = new ArrayList<>(orders.size());

        for (Order order : orders) {
            rows.add(fromOrder2TableRow(order, false));
        }
//        Page<OrderTableRow> results = new Page<>(page.getPageSize(), rows, page.getCurPage(), page.getPageCount(), page.getRecordCount());
        return rows;
    }

    private OrderTableRow fromOrder2TableRow(Order order, boolean validateOrders) throws WafException {
        OrderTableRow row = new OrderTableRow();
        String effectiveStartDate = order.getEffectiveStartDate();
        effectiveStartDate = StringUtils.left(effectiveStartDate, 16);
        String placedDate = order.getEffectiveStartDate();
        placedDate = StringUtils.left(placedDate, 10);
        row.setStartTime(effectiveStartDate);
        row.setPlacedDate(placedDate);
        String effectiveEndDate = order.getEffectiveEndDate();
        effectiveEndDate = StringUtils.left(effectiveEndDate, 10);
        row.setEndTime(effectiveEndDate);
        row.setFirstInstallment(new BigDecimal(order.getFirstInstallment()).setScale(2, RoundingMode.HALF_UP).toString());
        row.setTotalAmount(new BigDecimal(order.getTotalAmount()).setScale(2, RoundingMode.HALF_UP).toString());
        row.setPayInterval(String.valueOf(order.getPayInterval()));
        row.setChargeId(order.getChargeId());
        row.setPid(order.getPid());
        row.setIsFromOriginalContract(StringUtils.isNotBlank(order.getOriginalOrderId()));
        row.setState(order.getState());
        row.setProductName(productService.findById(order.getProductId()).getDisplayName());
        row.setOk(true);
        row.setSiteName(order.getSiteName());
        row.setContractName(order.getContractName());
        row.setContractId(order.getContractId());
        OrderBean bean = this.findOrderBeanById(order.getPid());
        if (validateOrders) {
            CompletionCheckResult completionCheckResult = OrderCompletionCheck.check(bean);
            if (!completionCheckResult.isOk()) {
                row.setOk(false);
                row.setErrorMessage(completionCheckResult.getMessage());
            } else {
                List<OrderBean> context = findOrderContextViaCache(order.getContractId());
                CollisionDetectResult collisionDetectResult = OrderCollisionDetect.check(bean, context);
                if (!collisionDetectResult.isOk()) {
                    row.setOk(false);
                    row.setErrorMessage(collisionDetectResult.getMessage());
                }
            }
        }
        return row;
    }


    private List<OrderBean> findOrderContextViaCache(String contractId) throws WafException {
//        if (contextCache.containsKey(contractId)) {
//            return contextCache.get(contractId);
//        } else {
        List<OrderBean> context = findOrderContext(contractId);
//            contextCache.put(contractId, context);
        return context;
//        }
    }


    private List<OrderBean> convert2beans(List<Order> orders) throws WafException {
        List<OrderBean> beans = new ArrayList<>();
        if (orders != null && orders.size() > 0) {
            for (Order order : orders) {
                beans.add(OrderBeanCache.get(order));
            }
        }
        return beans;

    }


    private void assertOrderCanBeTerminated(OrderBean order, OrderBean originalOrder, Date terminatedOn) throws WafException {
        if (StringUtils.isBlank(order.getOriginalOrderId())) {
            //只有处于变更中的订单可以被终止，即用户必须先对合同做变更操作
            throw new OperationDeniedException();
        }

        if (order.getState() != CommonState.DRAFT) {
            //处于变更中的订单，其状态为DRAFT
            throw new OnlyDraftingOrderAllowedException();
        }

        Date sysdate = DateUtil.getSystemDate();
        if (terminatedOn.before(DateUtil.beginOfToday())) {
            //如果订单中止时间早于系统时间，抛异常
            throw new InvalidTerminationDateException();
        }

        if (terminatedOn.before(order.getStartDate())) {
            //如果订单中止时间早于订购起始时间，抛异常
            throw new InvalidTerminationDateException();
        }

        if (terminatedOn.after(originalOrder.getEndDate())) {
            //如果订单终止时间晚于原订单结束时间，抛异常
            throw new InvalidTerminationDateException();
        }
    }


    private void assertAmountGreaterThanZero(BigDecimal amount) throws InvalidAmountException {
        if (amount.compareTo(new BigDecimal(0)) <= 0) {
            throw new InvalidAmountException();
        }
    }


    private void assertOrderCanBeUpdated(Order order) throws WafException {
        if (CommonState.valueOf(order.getState()) != CommonState.DRAFT) {
            throw new OnlyDraftingContractAllowedException();
        }

        if (StringUtils.isNotBlank(order.getOriginalOrderId())) {
            throw new UpdatingOriginalOrderNotAllowedException();
        }
    }

    private void assertFirstInstallmentNotZero(Order order) throws WafException {
        if (order.getSysFirstInstallment().compareTo(0d) <= 0) {
            throw new UpdateZeroFirstInstallmentNotAllowed();
        }
    }


    private void assertOrderCanBeDeleted(Order order) throws WafException {
        if (CommonState.valueOf(order.getState()) != CommonState.DRAFT) {
            throw new OnlyDraftingOrderAllowedException();
        }

        if (StringUtils.isNotBlank(order.getOriginalOrderId())) {
            throw new DeletingOriginalOrderIsNotAllowedException();
        }
    }


    public List<OrderBean> findMainProductOrdersByCustomer(String customerId, AccountPeriod accountPeriod) throws WafException {
        List<Order> orders = orderRepository.findMainProductOrders(customerId, accountPeriod);
        return convert2beans(orders);
    }

    public List<OrderBean> findMainPstnOrdersByCustomer(String customerId, AccountPeriod accountPeriod) throws WafException {
        List<Order> orders = orderRepository.findPstnProductOrders(customerId, accountPeriod);
        return convert2beans(orders);
    }

    public List<OrderBean> findPortsProductOrdersByCustomer(String customerId, AccountPeriod accountPeriod) throws WafException {
        List<Order> orders = orderRepository.findPortsProductOrders(customerId, accountPeriod);
        return convert2beans(orders);
    }

    public List<Order> findInEffectTrialProductOrdersByEndDate(String endDate) {
        List<Order> orders = orderRepository.findInEffectTrialProductOrdersByEndDate(endDate);
        return orders;
    }

    public List<Order> findInEffectNormalProductOrdersByEndDateAndAgentType(String endDate, String agentType) {
        List<Order> orders = orderRepository.findInEffectNormalProductOrdersByEndDateAndAgentType(endDate,agentType);
        return orders;
    }
}
