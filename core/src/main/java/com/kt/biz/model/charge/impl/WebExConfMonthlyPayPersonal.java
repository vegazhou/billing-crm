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
 * Created by Vega Zhou on 2017/5/8.
 */
public class WebExConfMonthlyPayPersonal extends AbstractChargeScheme {

    @ModelAttribute(value = SchemeKeys.COMMON_SITE)
    private String siteName;

    @ModelAttribute(value = SchemeKeys.MONTH_AMOUNT, type = AttributeType.INT)
    private int months = 0;

    @ModelAttribute(value = SchemeKeys.COMMON_UNIT_PRICE, type = AttributeType.FLOAT)
    private float unitPrice = 0f;

    @ModelAttribute(value = SchemeKeys.DISPLAY_NAME)
    private String userName;

    @ModelAttribute(value = SchemeKeys.FULL_NAME)
    private String fullName;

    @Override
    public ChargeType getType() {
        return ChargeType.MONTHLY_PAY_PERSONAL_WEBEX;
    }

    @Override
    public void copyChargeElementFrom(AbstractChargeScheme scheme) {
        if (scheme instanceof WebExConfMonthlyPayPersonal) {
            WebExConfMonthlyPayPersonal src = (WebExConfMonthlyPayPersonal) scheme;
            this.setUnitPrice(src.getUnitPrice());
            this.setSiteName(src.getSiteName());
        }
    }


    @Override
    public void copyUserInputElementFrom(AbstractChargeScheme scheme) {
        if (scheme instanceof WebExConfMonthlyPayPersonal) {
            WebExConfMonthlyPayPersonal src = (WebExConfMonthlyPayPersonal) scheme;
            this.setMonths(src.getMonths());
            this.setUserName(src.getUserName());
            this.setFullName(src.getFullName());
        }
    }

    @Override
    public Date calculateEndDate(Date startDate) {
        return OrderDurationUtil.getEndDate(startDate, months);
    }

    @Override
    public double calculateFirstInstallment(Date startDate, PayInterval payInterval) {
        int interval = payInterval.getInterval();
        int coverMonths = months > interval ? interval : months;
        if (payInterval.isOneTime()) {
            coverMonths = months;
        }
        return MathUtil.scale(coverMonths * unitPrice);
    }

    @Override
    public double calculateTotalAmount(Date start, Date end) {
        int months = OrderDurationUtil.getPaymentMonthsBetween(start, end);
        return MathUtil.scale(months * unitPrice);
    }


    @Override
    public CompletionCheckResult checkCompletion() {
        if (months <= 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的购买月数");
        }
        if (StringUtils.isBlank(siteName)) {
            return CompletionCheckResult.buildErrorResult("请选择产品适用的WebEx站点");
        }
        if (unitPrice < 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的月租单价");
        }
        if (StringUtils.isBlank(userName)) {
            return CompletionCheckResult.buildErrorResult("请输入购买者的邮箱");
        }
        return CompletionCheckResult.buildOkResult();
    }


    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
