package com.kt.biz.model.charge.impl;

import com.kt.biz.model.annotation.AttributeType;
import com.kt.biz.model.annotation.ModelAttribute;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.common.SchemeKeys;
import com.kt.biz.model.order.CompletionCheckResult;
import com.kt.biz.types.ChargeType;
import com.kt.biz.types.PayInterval;
import com.kt.util.DateUtil;
import com.kt.util.MathUtil;
import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vega on 2017/10/11.
 */
public class CcCallCenterPstnPackageCharge extends AbstractChargeScheme {

    @ModelAttribute(value = SchemeKeys.PSTN_PACKAGE_MINUTES, type = AttributeType.INT)
    private int packetMinutes = 0;

    @ModelAttribute(value = SchemeKeys.TOTAL_PRICE, type = AttributeType.FLOAT)
    private float packetPrice = 0F;

    @ModelAttribute(value = SchemeKeys.EFFECTIVE_BEFORE, type = AttributeType.DATE)
    private Date effectiveBefore = DateUtils.truncate(DateUtil.yesterday(), Calendar.DATE);

    @Override
    public ChargeType getType() {
        return ChargeType.CC_CALLCENTER_PSTN_PACKAGE;
    }

    @Override
    public CompletionCheckResult checkCompletion() {
        if (packetMinutes <= 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的包分钟数");
        }
        if (packetPrice < 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的价格");
        }
        return CompletionCheckResult.buildOkResult();
    }

    @Override
    public void copyChargeElementFrom(AbstractChargeScheme scheme) {
        if (scheme instanceof CcCallCenterPstnPackageCharge) {
            CcCallCenterPstnPackageCharge src = (CcCallCenterPstnPackageCharge) scheme;
            this.setPacketMinutes(src.getPacketMinutes());
            this.setPacketPrice(src.getPacketPrice());
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
        return MathUtil.scale(packetPrice);
    }

    @Override
    public double calculateTotalAmount(Date start, Date end) {
        return MathUtil.scale(packetPrice);
    }


    public int getPacketMinutes() {
        return packetMinutes;
    }

    public void setPacketMinutes(int packetMinutes) {
        this.packetMinutes = packetMinutes;
    }

    public float getPacketPrice() {
        return packetPrice;
    }

    public void setPacketPrice(float packetPrice) {
        this.packetPrice = packetPrice;
    }

    public Date getEffectiveBefore() {
        return effectiveBefore;
    }

    public void setEffectiveBefore(Date effectiveBefore) {
        this.effectiveBefore = effectiveBefore;
    }
}
