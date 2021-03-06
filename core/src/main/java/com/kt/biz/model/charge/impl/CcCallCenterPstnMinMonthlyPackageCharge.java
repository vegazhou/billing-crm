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
 * Created by Vega on 2017/11/23.
 */
public class CcCallCenterPstnMinMonthlyPackageCharge extends AbstractChargeScheme {
    @ModelAttribute(value = SchemeKeys.EFFECTIVE_BEFORE, type = AttributeType.DATE)
    private Date effectiveBefore = DateUtils.truncate(DateUtil.yesterday(), Calendar.DATE);

    @ModelAttribute(value = SchemeKeys.PSTN_PACKAGE_MINUTES, type = AttributeType.INT)
    private int minutesPerMonth;

    @ModelAttribute(value = SchemeKeys.COMMON_UNIT_PRICE, type = AttributeType.FLOAT)
    private float pricePerMinute;

    @Override
    public ChargeType getType() {
        return ChargeType.CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE;
    }

    @Override
    public CompletionCheckResult checkCompletion() {
        if (minutesPerMonth <= 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的最低消费分钟数");
        }
        if (pricePerMinute < 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的购买数量");
        }
        return CompletionCheckResult.buildOkResult();
    }

    @Override
    public void copyChargeElementFrom(AbstractChargeScheme scheme) {
        if (scheme instanceof CcCallCenterPstnMinMonthlyPackageCharge) {
            CcCallCenterPstnMinMonthlyPackageCharge src = (CcCallCenterPstnMinMonthlyPackageCharge) scheme;
            this.setMinutesPerMonth(src.getMinutesPerMonth());
            this.setPricePerMinute(src.getPricePerMinute());
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
        return MathUtil.scale(CcOrderDurationUtil.getFirstMonthPercentage(startDate) * minutesPerMonth * pricePerMinute);
    }

    @Override
    public double calculateTotalAmount(Date start, Date end) {
        double months = CcOrderDurationUtil.getPaymentMonthsBetween(start, end);
        return MathUtil.scale(months * minutesPerMonth * pricePerMinute);
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

    public int getMinutesPerMonth() {
        return minutesPerMonth;
    }

    public void setMinutesPerMonth(int minutesPerMonth) {
        this.minutesPerMonth = minutesPerMonth;
    }

    public float getPricePerMinute() {
        return pricePerMinute;
    }

    public void setPricePerMinute(float pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }
}
