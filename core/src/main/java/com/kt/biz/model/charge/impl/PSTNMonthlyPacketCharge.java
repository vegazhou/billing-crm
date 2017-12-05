package com.kt.biz.model.charge.impl;

import com.kt.biz.model.annotation.AttributeType;
import com.kt.biz.model.annotation.ModelAttribute;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.common.SchemeKeys;
import com.kt.biz.model.order.CompletionCheckResult;
import com.kt.biz.model.util.OrderDurationUtil;
import com.kt.biz.types.ChargeType;
import com.kt.biz.types.PayInterval;
import com.kt.entity.mysql.crm.Order;
import com.kt.entity.mysql.billing.PstnMonthlyPackage;
import com.kt.repo.mysql.billing.PstnMonthlyPackageRepository;
import com.kt.util.DateUtil;
import com.kt.util.MathUtil;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/8.
 */
public class PSTNMonthlyPacketCharge extends AbstractChargeScheme {

    private static Logger LOGGER = Logger.getLogger(PSTNMonthlyPacketCharge.class);

    @ModelAttribute(value = SchemeKeys.COMMON_SITES, type = AttributeType.STRING_LIST)
    private List<String> siteNames = new ArrayList<>();

    @ModelAttribute(value = SchemeKeys.MONTH_AMOUNT, type = AttributeType.INT)
    private int months = 0;

    @ModelAttribute(value = SchemeKeys.PSTN_PACKAGE_MINUTES, type = AttributeType.INT)
    private int minutesPerMonth;

    @ModelAttribute(value = SchemeKeys.MONTHLY_FEE, type = AttributeType.FLOAT)
    private float pricePerMonth;

    @ModelAttribute(value = SchemeKeys.PACKAGE_ID, type = AttributeType.INT)
    private int packageId;




    @Override
    public ChargeType getType() {
        return ChargeType.PSTN_MONTHLY_PACKET;
    }




    @Override
    public CompletionCheckResult checkCompletion() {
        if (siteNames.size() == 0) {
            return CompletionCheckResult.buildErrorResult("请选择产品适用的WebEx站点");
        }
        if (months <= 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的购买月数");
        }
        if (pricePerMonth < 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的月租费");
        }
        return CompletionCheckResult.buildOkResult();
    }

    @Override
    public void save() {
        savePackage();
        super.save();
    }

    @Override
    public void purge() {
        super.purge();
        deletePackage();
    }

    private void savePackage() {
        PstnMonthlyPackage pack = null;
        if (packageId == 0) {
            pack = new PstnMonthlyPackage();
        } else {
            pack = getRepository(PstnMonthlyPackageRepository.class).findOne((long) packageId);
        }
        pack.setDuration(months);
        pack.setMinutes(minutesPerMonth);
        Order order = findOrder();
        if (order != null) {
            pack.setStartDate(order.getEffectiveStartDate());
            try {
                pack.setEndDate(DateUtil.formatDate(DateUtil.xMonthLater(DateUtil.toDate(order.getEffectiveStartDate()), months)));
            } catch (ParseException ignore) {
                LOGGER.info("order effective start date format invalid", ignore);
            }
            pack.setOrderId(order.getPid());
            pack.setCustomerId(order.getCustomerId());
        } else {
            pack.setStartDate("1970-01-01 00:00:00");
            pack.setEndDate("1970-01-01 00:00:00");
        }
        pack = getRepository(PstnMonthlyPackageRepository.class).save(pack);
        packageId = pack.getId().intValue();
    }

    private void deletePackage() {
        getRepository(PstnMonthlyPackageRepository.class).delete((long) packageId);
    }


    @Override
    public void copyChargeElementFrom(AbstractChargeScheme scheme) {
        if (scheme instanceof PSTNMonthlyPacketCharge) {
            PSTNMonthlyPacketCharge src = (PSTNMonthlyPacketCharge) scheme;
            this.setMinutesPerMonth(src.getMinutesPerMonth());
            this.setPricePerMonth(src.getPricePerMonth());
        }
    }

    @Override
    public void copyUserInputElementFrom(AbstractChargeScheme scheme) {
        if (scheme instanceof PSTNMonthlyPacketCharge) {
            PSTNMonthlyPacketCharge src = (PSTNMonthlyPacketCharge) scheme;
            this.months = src.getMonths();
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
        return MathUtil.scale(coverMonths * pricePerMonth);
    }

    @Override
    public double calculateTotalAmount(Date start, Date end) {
        int months = OrderDurationUtil.getPaymentMonthsBetween(start, end);
        return MathUtil.scale(months * pricePerMonth);
    }

    public List<String> getSiteNames() {
        return siteNames;
    }

    public void setSiteNames(List<String> siteNames) {
        this.siteNames = siteNames;
    }

    public int getMinutesPerMonth() {
        return minutesPerMonth;
    }

    public void setMinutesPerMonth(int minutesPerMonth) {
        this.minutesPerMonth = minutesPerMonth;
    }

    public float getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(float pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }
}
