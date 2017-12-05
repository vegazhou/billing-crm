package com.kt.biz.model.charge.impl;

import com.kt.biz.model.annotation.AttributeType;
import com.kt.biz.model.annotation.ModelAttribute;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.common.SchemeKeys;
import com.kt.biz.model.order.CompletionCheckResult;
import com.kt.biz.model.util.OrderDurationUtil;
import com.kt.biz.types.ChargeType;
import com.kt.biz.types.PayInterval;
import com.kt.entity.mysql.crm.EcTierRate;
import com.kt.util.MathUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/8.
 */
public class WebExECPrepaid extends AbstractChargeScheme {
    @ModelAttribute(value = SchemeKeys.COMMON_SITE)
    private String siteName;

    @ModelAttribute(value = SchemeKeys.TOTAL_PRICE, type = AttributeType.FLOAT)
    private float amount;

    private List<EcTierRate> unitPriceList = new ArrayList<>();

    @ModelAttribute(value = SchemeKeys.MONTH_AMOUNT, type = AttributeType.INT)
    private int months;

    @Override
    public ChargeType getType() {
        return ChargeType.EC_PREPAID;
    }


    @Override
    public void copyChargeElementFrom(AbstractChargeScheme scheme) {

    }

    @Override
    public void copyUserInputElementFrom(AbstractChargeScheme scheme) {

    }

    @Override
    public Date calculateEndDate(Date startDate) {
        return OrderDurationUtil.getEndDate(startDate, months);
    }

    @Override
    public CompletionCheckResult checkCompletion() {
        if (amount <= 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的金额");
        }
        if (StringUtils.isBlank(siteName)) {
            return CompletionCheckResult.buildErrorResult("请选择产品适用的WebEx站点");
        }
        if (months <= 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的生效月份数");
        }
        return CompletionCheckResult.buildOkResult();
    }

    @Override
    public double calculateFirstInstallment(Date startDate, PayInterval payInterval) {
        return MathUtil.scale(amount);
    }

    @Override
    public double calculateTotalAmount(Date start, Date end) {
        return 0;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
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
}
