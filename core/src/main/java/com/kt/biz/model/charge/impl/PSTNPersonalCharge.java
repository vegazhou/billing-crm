package com.kt.biz.model.charge.impl;

import com.google.gson.JsonObject;
import com.kt.biz.model.annotation.AttributeType;
import com.kt.biz.model.annotation.ModelAttribute;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.common.DefaultPstnRates;
import com.kt.biz.model.common.SchemeKeys;
import com.kt.biz.model.order.CompletionCheckResult;
import com.kt.biz.model.util.Copier;
import com.kt.biz.types.ChargeType;
import com.kt.biz.types.PayInterval;
import com.kt.entity.mysql.crm.Rate;
import com.kt.exception.EntityNotFoundException;
import com.kt.repo.mysql.batch.RateRepository;
import com.kt.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Vega Zhou on 2017/5/8.
 */
public class PSTNPersonalCharge extends AbstractChargeScheme {
    @ModelAttribute(value = SchemeKeys.COMMON_SITE)
    private String siteName;

    @ModelAttribute(value = SchemeKeys.PSTN_RATES_ID, changeable = false)
    private String unitPriceListId = UUID.randomUUID().toString();

    private List<Rate> unitPriceList = DefaultPstnRates.getDefaultPstnRates();

    @ModelAttribute(value = SchemeKeys.EFFECTIVE_BEFORE, type = AttributeType.DATE)
    private Date effectiveBefore = DateUtils.truncate(DateUtil.yesterday(), Calendar.DATE);

    @ModelAttribute(value = SchemeKeys.DISPLAY_NAME)
    private String userName;

    @Override
    public ChargeType getType() {
        return ChargeType.PSTN_PERSONAL_CHARGE;
    }

    @Override
    public void save() {
        super.save();
        savePriceList();
    }

    @Override
    public void load(String id) throws EntityNotFoundException {
        super.load(id);
        loadPriceList();
    }

    @Override
    public void purge() {
        super.purge();
        deletePriceList();
    }


    @Override
    public CompletionCheckResult checkCompletion() {
        if (StringUtils.isBlank(siteName)) {
            return CompletionCheckResult.buildErrorResult("请选择产品适用的WebEx站点");
        }
        if (StringUtils.isBlank(userName)) {
            return CompletionCheckResult.buildErrorResult("请输入购买者的邮箱");
        }
        return CompletionCheckResult.buildOkResult();
    }

    private void savePriceList() {
        RateRepository repository = getRepository(RateRepository.class);
        for (Rate rate : unitPriceList) {
            rate.setPid(unitPriceListId);
            repository.save(rate);
        }
    }

    private void loadPriceList() {
        unitPriceList = getRepository(RateRepository.class).findByPid(unitPriceListId);
    }

    private void deletePriceList() {
        getRepository(RateRepository.class).deleteByPid(unitPriceListId);
    }

    @Override
    public void loadFromJson(JsonObject json) {
        super.loadFromJson(json);
        unitPriceList = getRepository(RateRepository.class).findByPid(unitPriceListId);
    }

    @Override
    public void copyChargeElementFrom(AbstractChargeScheme scheme) {
        if (scheme instanceof PSTNPersonalCharge) {
            PSTNPersonalCharge src = (PSTNPersonalCharge) scheme;
            this.unitPriceList = Copier.copy(src.getUnitPriceList());
            this.siteName = src.getSiteName();
        }
    }

    @Override
    public void copyUserInputElementFrom(AbstractChargeScheme scheme) {
        if (scheme instanceof PSTNPersonalCharge) {
            PSTNPersonalCharge src = (PSTNPersonalCharge) scheme;
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
        return 0;
    }

    @Override
    public double calculateTotalAmount(Date start, Date end) {
        return 0;
    }


    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getUnitPriceListId() {
        return unitPriceListId;
    }

    public void setUnitPriceListId(String unitPriceListId) {
        this.unitPriceListId = unitPriceListId;
    }

    public List<Rate> getUnitPriceList() {
        return unitPriceList;
    }

    public void setUnitPriceList(List<Rate> unitPriceList) {
        this.unitPriceList = unitPriceList;
    }

    public Date getEffectiveBefore() {
        return effectiveBefore;
    }

    public void setEffectiveBefore(Date effectiveBefore) {
        this.effectiveBefore = effectiveBefore;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
