package com.kt.biz.model.order;

import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.model.biz.AbstractBizScheme;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.order.util.TimeSpan;
import com.kt.biz.types.ChargeType;
import com.kt.biz.types.CommonState;
import com.kt.biz.types.PayInterval;

import java.util.Date;

/**
 * Created by Vega Zhou on 2016/3/21.
 */
public class OrderBean {

    private String id;

    private String contractId;

    private PayInterval payInterval;

    private CommonState state;

    private Date startDate;

    private Date endDate;

    private Date placedDate;

    private String originalOrderId;

    private AbstractBizScheme biz;

    private AbstractChargeScheme chargeScheme;
    //add by garfield for Invoice
    private String customerId;

    private String productId;

    public OrderBean(String id) {
        assert id != null;
        this.id = id;
    }

    public double calculateFirstInstallment() {
        return chargeScheme.calculateFirstInstallment(startDate, payInterval);
    }

    public double calculateTotalAmount() {
        return chargeScheme.calculateTotalAmount(startDate, endDate);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public AbstractBizScheme getBiz() {
        return biz;
    }

    public void setBiz(AbstractBizScheme biz) {
        this.biz = biz;
    }

    public AbstractChargeScheme getChargeScheme() {
        return chargeScheme;
    }

    public void setChargeScheme(AbstractChargeScheme chargeScheme) {
        this.chargeScheme = chargeScheme;
    }

    public PayInterval getPayInterval() {
        return payInterval;
    }

    public void setPayInterval(PayInterval payInterval) {
        this.payInterval = payInterval;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getOriginalOrderId() {
        return originalOrderId;
    }

    public void setOriginalOrderId(String originalOrderId) {
        this.originalOrderId = originalOrderId;
    }

    public CommonState getState() {
        return state;
    }

    public void setState(CommonState state) {
        this.state = state;
    }

    public Date getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(Date placedDate) {
        this.placedDate = placedDate;
    }

    
    public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public boolean isEffectiveInAccountPeriod(AccountPeriod accountPeriod) {
        TimeSpan span = new TimeSpan(accountPeriod.beginOfThisPeriod(), accountPeriod.endOfThisPeriod());
        TimeSpan span2 = new TimeSpan(this.getStartDate(), this.getEndDate());
        return span.isOverlappedWith(span2);
    }

	@Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof OrderBean && ((OrderBean) obj).getId().equals(id);
    }


    public boolean isCallCenterOrder() {
        ChargeType chargeType = chargeScheme.getType();
        return chargeType == ChargeType.CC_CALLCENTER_MONTHLY_PAY ||
                chargeType == ChargeType.CC_CALLCENTER_NUMBER_MONTHLY_PAY ||
                chargeType == ChargeType.CC_CALLCENTER_PSTN ||
                chargeType == ChargeType.CC_CALLCENTER_PSTN_PACKAGE ||
                chargeType == ChargeType.CC_CALLCENTER_PSTN_MONTHLY_PACKAGE ||
                chargeType == ChargeType.CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE ||
                chargeType == ChargeType.CC_CALLCENTER_OLS_MONTHLY_PAY;
    }
}
