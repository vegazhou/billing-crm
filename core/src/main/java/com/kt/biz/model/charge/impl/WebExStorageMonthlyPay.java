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
public class WebExStorageMonthlyPay extends AbstractChargeScheme {
    @ModelAttribute(value = SchemeKeys.COMMON_SITE)
    private String siteName;

    @ModelAttribute(value = SchemeKeys.STORAGE_SIZE, type = AttributeType.INT)
    private int storageSize;

    @ModelAttribute(value = SchemeKeys.MONTH_AMOUNT, type = AttributeType.INT)
    private int month;

    @ModelAttribute(value = SchemeKeys.COMMON_UNIT_PRICE, type = AttributeType.FLOAT)
    private float unitPrice;

    @Override
    public ChargeType getType() {
        return ChargeType.MONTHLY_PAY_BY_STORAGE;
    }

    @Override
    public void copyChargeElementFrom(AbstractChargeScheme scheme) {
        if (scheme instanceof WebExStorageMonthlyPay) {
            WebExStorageMonthlyPay src = (WebExStorageMonthlyPay) scheme;
            this.setUnitPrice(src.getUnitPrice());
        }
    }

    @Override
    public void copyUserInputElementFrom(AbstractChargeScheme scheme) {
        if (scheme instanceof WebExStorageMonthlyPay) {
            WebExStorageMonthlyPay src = (WebExStorageMonthlyPay) scheme;
            setStorageSize(src.getStorageSize());
            setMonth(src.getMonth());
        }
    }

    @Override
    public Date calculateEndDate(Date startDate) {
        return OrderDurationUtil.getEndDate(startDate, getMonth());
    }

    @Override
    public double calculateFirstInstallment(Date startDate, PayInterval payInterval) {
        int interval = payInterval.getInterval();
        int coverMonths = month > interval ? interval : month;
        if (payInterval.isOneTime()) {
            coverMonths = month;
        }
        return MathUtil.scale(coverMonths * storageSize * unitPrice);
    }

    @Override
    public CompletionCheckResult checkCompletion() {
        if (storageSize <= 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的存储大小");
        }
        if (month <= 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的购买月数");
        }
        if (StringUtils.isBlank(siteName)) {
            return CompletionCheckResult.buildErrorResult("请选择产品适用的WebEx站点");
        }
        if (unitPrice < 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的单价");
        }
        return CompletionCheckResult.buildOkResult();
    }

    @Override
    public double calculateTotalAmount(Date start, Date end) {
        int month = OrderDurationUtil.getPaymentMonthsBetween(start, end);
        return MathUtil.scale(month * storageSize * unitPrice);
    }

    public int getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(int storageSize) {
        this.storageSize = storageSize;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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
