package com.kt.biz.model.charge.impl;

import com.kt.biz.model.annotation.AttributeType;
import com.kt.biz.model.annotation.ModelAttribute;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.common.SchemeKeys;
import com.kt.biz.model.order.CollisionDetectResult;
import com.kt.biz.model.order.CompletionCheckResult;
import com.kt.biz.types.ChargeType;
import com.kt.biz.types.PayInterval;
import com.kt.entity.mysql.billing.PstnSinglePackage;
import com.kt.entity.mysql.crm.Order;
import com.kt.repo.mysql.billing.PstnSinglePackageRepository;
import com.kt.util.DateUtil;
import com.kt.util.MathUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vega Zhou on 2017/5/8.
 */
public class PSTNPersonalPacket extends AbstractChargeScheme {

    private static final Logger LOGGER = Logger.getLogger(PSTNPersonalPacket.class);

    @ModelAttribute(value = SchemeKeys.COMMON_SITE)
    private String siteName;

    @ModelAttribute(value = SchemeKeys.PSTN_PACKAGE_MINUTES, type = AttributeType.INT)
    private int packetMinutes = 0;

    @ModelAttribute(value = SchemeKeys.TOTAL_PRICE, type = AttributeType.FLOAT)
    private float packetPrice = 0F;

    @ModelAttribute(value = SchemeKeys.EFFECTIVE_BEFORE, type = AttributeType.DATE)
    private Date effectiveBefore = DateUtils.truncate(DateUtil.yesterday(), Calendar.DATE);

    @ModelAttribute(value = SchemeKeys.PACKAGE_ID, type = AttributeType.INT)
    private int packageId;

    @ModelAttribute(value = SchemeKeys.DISPLAY_NAME)
    private String userName;

    @Override
    public ChargeType getType() {
        return ChargeType.PSTN_PERSONAL_PACKET;
    }


    @Override
    public CompletionCheckResult checkCompletion() {
        if (StringUtils.isBlank(siteName)) {
            return CompletionCheckResult.buildErrorResult("请选择WebEx站点");
        }
        if (packetMinutes <= 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的包分钟数");
        }
        if (packetPrice < 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的价格");
        }
        if (StringUtils.isBlank(userName)) {
            return CompletionCheckResult.buildErrorResult("请输入购买者的邮箱");
        }
        return CompletionCheckResult.buildOkResult();
    }

    @Override
    public void copyChargeElementFrom(AbstractChargeScheme scheme) {
        if (scheme instanceof PSTNPersonalPacket) {
            PSTNPersonalPacket src = (PSTNPersonalPacket) scheme;
            this.setPacketMinutes(src.getPacketMinutes());
            this.setPacketPrice(src.getPacketPrice());
            this.setSiteName(src.getSiteName());
        }
    }


    @Override
    public void copyUserInputElementFrom(AbstractChargeScheme scheme) {
        if (scheme instanceof PSTNPersonalPacket) {
            PSTNPersonalPacket src = (PSTNPersonalPacket) scheme;
            this.setEffectiveBefore(src.getEffectiveBefore());
            this.setUserName(src.getUserName());
        }
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
        //TODO: 个人语音包怎么保存，需要再讨论

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
                e.printStackTrace();
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





    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
