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
import com.kt.entity.mysql.billing.PstnSinglePackage;
import com.kt.repo.mysql.billing.PstnSinglePackageRepository;
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
public class PSTNSinglePacket4MultiSites extends AbstractChargeScheme {

    private static final Logger LOGGER = Logger.getLogger(PSTNSinglePacket4MultiSites.class);

    @ModelAttribute(value = SchemeKeys.COMMON_SITES, type = AttributeType.STRING_LIST)
    private List<String> siteNames = new ArrayList<>();

    @ModelAttribute(value = SchemeKeys.PSTN_PACKAGE_MINUTES, type = AttributeType.INT)
    private int packetMinutes = 0;

    @ModelAttribute(value = SchemeKeys.TOTAL_PRICE, type = AttributeType.FLOAT)
    private float packetPrice = 0F;

    @ModelAttribute(value = SchemeKeys.MONTH_AMOUNT, type = AttributeType.INT)
    private int months;

    @ModelAttribute(value = SchemeKeys.PACKAGE_ID, type = AttributeType.INT)
    private int packageId;


    @Override
    public ChargeType getType() {
        return ChargeType.PSTN_SINGLE_PACKET_FOR_MULTIPLE_SITES;
    }


    @Override
    public CompletionCheckResult checkCompletion() {
        if (siteNames.size() == 0) {
            return CompletionCheckResult.buildErrorResult("请选择WebEx站点");
        }
        if (packetMinutes <= 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的包分钟数");
        }
        if (months <= 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的生效月数");
        }
        if (packetPrice < 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的价格");
        }
        return CompletionCheckResult.buildOkResult();
    }

    @Override
    public void copyChargeElementFrom(AbstractChargeScheme scheme) {
        if (scheme instanceof PSTNSinglePacket4MultiSites) {
            PSTNSinglePacket4MultiSites src = (PSTNSinglePacket4MultiSites) scheme;
            this.setPacketMinutes(src.getPacketMinutes());
            this.setPacketPrice(src.getPacketPrice());
        }
    }


    @Override
    public void copyUserInputElementFrom(AbstractChargeScheme scheme) {
        if (scheme instanceof PSTNSinglePacket4MultiSites) {
            PSTNSinglePacket4MultiSites src = (PSTNSinglePacket4MultiSites) scheme;
            this.setMonths(src.getMonths());
        }
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
        PstnSinglePackage pack = null;
        if (packageId == 0) {
            pack = new PstnSinglePackage();
        } else {
            pack = getRepository(PstnSinglePackageRepository.class).findOne((long) packageId);
        }
        pack.setTotalMinutes(packetMinutes);

        Order order = findOrder();
        if (order != null) {
            pack.setStartDate(order.getEffectiveStartDate());
            try {
                pack.setEndDate(DateUtil.formatDate(calculateEndDate(DateUtil.toDate(order.getEffectiveStartDate()))));
            } catch (ParseException e) {
                LOGGER.info("order effective start date is not a valid date format", e);
            }
            pack.setOrderId(order.getPid());
            pack.setCustomerId(order.getCustomerId());
        } else {
            pack.setStartDate("1970-01-01 00:00:00");
            pack.setEndDate("1970-01-01 00:00:00");
        }
        pack = getRepository(PstnSinglePackageRepository.class).save(pack);
        packageId = pack.getId().intValue();
    }

    private void deletePackage() {
        getRepository(PstnSinglePackageRepository.class).delete((long) packageId);
    }


    @Override
    public Date calculateEndDate(Date startDate) {
        return OrderDurationUtil.getEndDate(startDate, months);
    }

    @Override
    public double calculateFirstInstallment(Date startDate, PayInterval payInterval) {
        return MathUtil.scale(packetPrice);
    }

    @Override
    public double calculateTotalAmount(Date start, Date end) {
        return MathUtil.scale(packetPrice);
    }

    public List<String> getSiteNames() {
        return siteNames;
    }

    public void addSiteName(String siteName) {
        this.siteNames.add(siteName);
    }

    public void setSiteNames(List<String> siteNames) {
        this.siteNames = siteNames;
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

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }
}
