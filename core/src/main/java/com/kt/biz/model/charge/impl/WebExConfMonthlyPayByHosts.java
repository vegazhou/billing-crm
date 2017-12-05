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
 * Created by Vega Zhou on 2016/3/8.
 */
public class WebExConfMonthlyPayByHosts extends AbstractChargeScheme {
    @ModelAttribute(value = SchemeKeys.COMMON_SITE)
    private String siteName;

    @ModelAttribute(value = SchemeKeys.HOSTS_AMOUNT, type = AttributeType.INT)
    private int hosts = 0;

    @ModelAttribute(value = SchemeKeys.MONTH_AMOUNT, type = AttributeType.INT)
    private int months = 0;

    @ModelAttribute(value = SchemeKeys.COMMON_UNIT_PRICE, type = AttributeType.FLOAT)
    private float unitPrice = 0f;

    @Override
    public ChargeType getType() {
        return ChargeType.MONTHLY_PAY_BY_HOSTS;
    }

    @Override
    public void copyChargeElementFrom(AbstractChargeScheme scheme) {
        if (scheme instanceof WebExConfMonthlyPayByHosts) {
            WebExConfMonthlyPayByHosts src = (WebExConfMonthlyPayByHosts) scheme;
            this.setUnitPrice(src.getUnitPrice());
        }
    }

    @Override
    public void copyUserInputElementFrom(AbstractChargeScheme scheme) {
        if (scheme instanceof WebExConfMonthlyPayByHosts) {
            WebExConfMonthlyPayByHosts src = (WebExConfMonthlyPayByHosts) scheme;
            this.setMonths(src.getMonths());
            this.setHosts(src.getHosts());
        }
    }

    @Override
    public Date calculateEndDate(Date startDate) {
        return OrderDurationUtil.getEndDate(startDate, months);
    }

    @Override
    public CompletionCheckResult checkCompletion() {
        if (hosts <=0) {
            return CompletionCheckResult.buildErrorResult("请正确输入购买用户数");
        }
        if (months <= 0) {
            return CompletionCheckResult.buildErrorResult("请正确输入购买月数");
        }
        if (StringUtils.isBlank(siteName)) {
            return CompletionCheckResult.buildErrorResult("请选择产品适用的WebEx站点");
        }
        if (unitPrice < 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的月租费");
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
        return MathUtil.scale(coverMonths * hosts * unitPrice);
    }

    @Override
    public double calculateTotalAmount(Date start, Date end) {
        int months = OrderDurationUtil.getPaymentMonthsBetween(start, end);
        return MathUtil.scale(hosts * months * unitPrice);
    }

    public int getHosts() {
        return hosts;
    }

    public void setHosts(int hosts) {
        this.hosts = hosts;
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

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
}
