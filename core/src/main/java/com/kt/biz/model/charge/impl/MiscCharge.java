package com.kt.biz.model.charge.impl;

import com.kt.biz.model.annotation.AttributeType;
import com.kt.biz.model.annotation.ModelAttribute;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.common.SchemeKeys;
import com.kt.biz.model.order.CompletionCheckResult;
import com.kt.biz.types.ChargeType;
import com.kt.biz.types.PayInterval;

import java.util.Date;

/**
 * Created by Vega Zhou on 2016/6/7.
 */
public class MiscCharge extends AbstractChargeScheme {

    @ModelAttribute(value = SchemeKeys.TOTAL_PRICE, type = AttributeType.FLOAT)
    private float totalPrice = 0f;

    @Override
    public ChargeType getType() {
        return ChargeType.MISC_CHARGE;
    }

    @Override
    public void copyChargeElementFrom(AbstractChargeScheme scheme) {
        totalPrice = ((MiscCharge) scheme).getTotalPrice();
    }

    @Override
    public void copyUserInputElementFrom(AbstractChargeScheme scheme) {

    }

    @Override
    public Date calculateEndDate(Date startDate) {
        return startDate;
    }

    @Override
    public double calculateFirstInstallment(Date startDate, PayInterval payInterval) {
        return totalPrice;
    }

    @Override
    public double calculateTotalAmount(Date start, Date end) {
        return totalPrice;
    }

    @Override
    public CompletionCheckResult checkCompletion() {
        if (totalPrice <= 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的金额");
        }
        return CompletionCheckResult.buildOkResult();
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
