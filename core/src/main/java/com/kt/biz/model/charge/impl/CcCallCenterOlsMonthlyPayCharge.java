package com.kt.biz.model.charge.impl;

import com.kt.biz.model.annotation.AttributeType;
import com.kt.biz.model.annotation.ModelAttribute;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.common.SchemeKeys;
import com.kt.biz.model.order.CompletionCheckResult;
import com.kt.biz.model.util.CcOrderDurationUtil;
import com.kt.biz.types.ChargeType;
import com.kt.biz.types.PayInterval;
import com.kt.util.DateUtil;
import com.kt.util.MathUtil;
import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vega on 2017/10/12.
 */
public class CcCallCenterOlsMonthlyPayCharge extends AbstractChargeScheme {

    @ModelAttribute(value = SchemeKeys.HOSTS_AMOUNT, type = AttributeType.INT)
    private int hosts = 0;

    @ModelAttribute(value = SchemeKeys.EFFECTIVE_BEFORE, type = AttributeType.DATE)
    private Date effectiveBefore = DateUtils.truncate(DateUtil.yesterday(), Calendar.DATE);

    @ModelAttribute(value = SchemeKeys.COMMON_UNIT_PRICE, type = AttributeType.FLOAT)
    private float unitPrice = 0f;

    @Override
    public ChargeType getType() {
        return ChargeType.CC_CALLCENTER_OLS_MONTHLY_PAY;
    }

    @Override
    public void copyChargeElementFrom(AbstractChargeScheme scheme) {
        if (scheme instanceof CcCallCenterOlsMonthlyPayCharge) {
            CcCallCenterOlsMonthlyPayCharge src = (CcCallCenterOlsMonthlyPayCharge) scheme;
            this.setUnitPrice(src.getUnitPrice());
        }
    }

    @Override
    public void copyUserInputElementFrom(AbstractChargeScheme scheme) {

    }

    @Override
    public Date calculateEndDate(Date startDate) {
        return effectiveBefore;
    }

    @Override
    public double calculateFirstInstallment(Date startDate, PayInterval payInterval) {
        return MathUtil.scale(CcOrderDurationUtil.getFirstMonthPercentage(startDate) * hosts * unitPrice);
    }

    @Override
    public double calculateTotalAmount(Date start, Date end) {
        double months = CcOrderDurationUtil.getPaymentMonthsBetween(start, end);
        return MathUtil.scale(hosts * months * unitPrice);
    }

    @Override
    public CompletionCheckResult checkCompletion() {
        if (hosts <=0) {
            return CompletionCheckResult.buildErrorResult("请正确输入购买用户数");
        }
        if (unitPrice < 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的月租费");
        }
        return CompletionCheckResult.buildOkResult();
    }


    public int getHosts() {
        return hosts;
    }

    public void setHosts(int hosts) {
        this.hosts = hosts;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Date getEffectiveBefore() {
        return effectiveBefore;
    }

    public void setEffectiveBefore(Date effectiveBefore) {
        if (DateUtil.isFirstDayOfMonth(effectiveBefore)) {
            this.effectiveBefore = effectiveBefore;
        } else {
            this.effectiveBefore = DateUtil.beginOfMonth(DateUtil.oneMonthLater(effectiveBefore));
        }
    }
}
