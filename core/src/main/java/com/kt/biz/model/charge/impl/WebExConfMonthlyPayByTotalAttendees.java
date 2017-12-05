package com.kt.biz.model.charge.impl;

import com.kt.biz.model.annotation.AttributeType;
import com.kt.biz.model.annotation.ModelAttribute;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.common.SchemeKeys;
import com.kt.biz.model.order.CompletionCheckResult;
import com.kt.biz.model.util.OrderDurationUtil;
import com.kt.biz.types.ChargeType;
import com.kt.biz.types.PayInterval;
import com.kt.util.MathUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * Created by Vega Zhou on 2017/4/25.
 */
public class WebExConfMonthlyPayByTotalAttendees extends AbstractChargeScheme {
    @ModelAttribute(value = SchemeKeys.COMMON_SITE)
    private String siteName;

    @ModelAttribute(value = SchemeKeys.PORTS_AMOUNT, type = AttributeType.INT)
    private int ports = 0;

    @ModelAttribute(value = SchemeKeys.MONTH_AMOUNT, type = AttributeType.INT)
    private int months = 0;

    @ModelAttribute(value = SchemeKeys.COMMON_UNIT_PRICE, type = AttributeType.FLOAT)
    private float unitPrice = 0f;

    @ModelAttribute(value = SchemeKeys.COMMON_OVERFLOW_UNIT_PRICE, type = AttributeType.FLOAT)
    private float overflowUnitPrice = 0f;

    @Override
    public ChargeType getType() {
        return ChargeType.MONTHLY_PAY_BY_TOTAL_ATTENDEES;
    }

    @Override
    public void copyChargeElementFrom(AbstractChargeScheme scheme) {
        if (scheme instanceof WebExConfMonthlyPayByTotalAttendees) {
            WebExConfMonthlyPayByTotalAttendees src = (WebExConfMonthlyPayByTotalAttendees) scheme;
            this.setOverflowUnitPrice(src.getOverflowUnitPrice());
            this.setUnitPrice(src.getUnitPrice());
        }
    }

    @Override
    public void copyUserInputElementFrom(AbstractChargeScheme scheme) {
        if (scheme instanceof WebExConfMonthlyPayByTotalAttendees) {
            WebExConfMonthlyPayByTotalAttendees src = (WebExConfMonthlyPayByTotalAttendees) scheme;
            this.setPorts(src.getPorts());
            this.setMonths(src.getMonths());
        }
    }

    @Override
    public Date calculateEndDate(Date startDate) {
        return OrderDurationUtil.getEndDate(startDate, months);
    }

    @Override
    public CompletionCheckResult checkCompletion() {
        if (ports < 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的总参会人次数");
        }
        if (months <= 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的购买月数");
        }
        if (StringUtils.isBlank(siteName)) {
            return CompletionCheckResult.buildErrorResult("请选择产品适用的WebEx站点");
        }
        if (unitPrice < 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的月租单价");
        }
        if (overflowUnitPrice < 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的溢出单价");
        }
        return CompletionCheckResult.buildOkResult();
    }

    @Override
    public double calculateFirstInstallment(Date startDate, PayInterval payInterval) {
        int interval = payInterval.getInterval();
        int coverMonths = months > interval ? interval : months;
        if (payInterval.isOneTime()) {
            coverMonths = months;
        }
        return MathUtil.scale(coverMonths * ports * unitPrice);
    }

    @Override
    public double calculateTotalAmount(Date start, Date end) {
        int months = OrderDurationUtil.getPaymentMonthsBetween(start, end);
        return MathUtil.scale(months * ports * unitPrice);
    }

    public int getPorts() {
        return ports;
    }

    public void setPorts(int ports) {
        this.ports = ports;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getOverflowUnitPrice() {
        return overflowUnitPrice;
    }

    public void setOverflowUnitPrice(float overflowUnitPrice) {
        this.overflowUnitPrice = overflowUnitPrice;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
}

