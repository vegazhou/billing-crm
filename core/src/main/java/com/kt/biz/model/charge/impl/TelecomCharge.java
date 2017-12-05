package com.kt.biz.model.charge.impl;

import com.kt.biz.model.annotation.AttributeType;
import com.kt.biz.model.annotation.ModelAttribute;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.common.SchemeKeys;
import com.kt.biz.model.order.CompletionCheckResult;
import com.kt.biz.model.util.OrderDurationUtil;
import com.kt.biz.types.ChargeType;
import com.kt.biz.types.PayInterval;
import com.kt.biz.validators.WebExPasswordValidator;
import com.kt.util.MathUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * Created by Vega Zhou on 2016/4/14.
 */
public class TelecomCharge extends AbstractChargeScheme {
    @ModelAttribute(value = SchemeKeys.COMMON_SITE)
    private String siteName;

    @ModelAttribute(value = SchemeKeys.HOSTS_AMOUNT, type = AttributeType.INT)
    private int hosts = 0;

    @ModelAttribute(value = SchemeKeys.MONTH_AMOUNT, type = AttributeType.INT)
    private int months = 0;

    @ModelAttribute(value = SchemeKeys.COMMON_UNIT_PRICE, type = AttributeType.FLOAT)
    private float unitPrice = 0f;

    @ModelAttribute(value = SchemeKeys.WEBEX_ID)
    private String webexId;

    @ModelAttribute(value = SchemeKeys.ENTERPRISE_CODE)
    private String enterpriseCode;

    @ModelAttribute(value = SchemeKeys.ENTERPRISE_NAME)
    private String enterpriseName;

    @ModelAttribute(value = SchemeKeys.INITIAL_PASSWORD)
    private String initialPassword;

    @ModelAttribute(value = SchemeKeys.DISPLAY_NAME)
    private String userName;

    @Override
    public ChargeType getType() {
        return ChargeType.TELECOM_CHARGE;
    }

    @Override
    public void copyChargeElementFrom(AbstractChargeScheme scheme) {
        if (scheme instanceof TelecomCharge) {
            TelecomCharge src = (TelecomCharge) scheme;
            this.setUnitPrice(src.getUnitPrice());
        }
    }

    @Override
    public void copyUserInputElementFrom(AbstractChargeScheme scheme) {

    }

    @Override
    public Date calculateEndDate(Date startDate) {
//        if(months==99) {
//            return new Date(2050 - 1900, 0, 1);
//        }else{
            return OrderDurationUtil.getEndDate(startDate, months);
//        }
    }

    @Override
    public double calculateFirstInstallment(Date startDate, PayInterval payInterval) {
        return 0;
    }

    @Override
    public double calculateTotalAmount(Date start, Date end) {
        int months = OrderDurationUtil.getPaymentMonthsBetween(start, end);
        return MathUtil.scale(months * unitPrice);
    }

    @Override
    public CompletionCheckResult checkCompletion() {
        if (StringUtils.isBlank(siteName)) {
            return CompletionCheckResult.buildErrorResult("请选择产品适用的WebEx站点");
        }
        if (unitPrice < 0) {
            return CompletionCheckResult.buildErrorResult("请输入正确的月租费");
        }
        if (StringUtils.isBlank(webexId)) {
            return CompletionCheckResult.buildErrorResult("请输入正确的WebEx ID");
        }
        if (StringUtils.isBlank(enterpriseCode)) {
            return CompletionCheckResult.buildErrorResult("请输入正确的企业编码");
        }
        if (StringUtils.isBlank(enterpriseName)) {
            return CompletionCheckResult.buildErrorResult("请输入正确的企业名称");
        }
        if (StringUtils.isBlank(userName)) {
            return CompletionCheckResult.buildErrorResult("请输入正确的用户姓名");
        }
        if (StringUtils.isBlank(initialPassword)) {
            return CompletionCheckResult.buildErrorResult("请输入正确的用户初始密码");
        }
        if (!WebExPasswordValidator.isValid(initialPassword)) {
            return CompletionCheckResult.buildErrorResult("用户初始密码不符合密码规则，请重新输入");
        }
        return CompletionCheckResult.buildOkResult();
    }

    //============================= GETTER AND SETTER ===============================

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getWebexId() {
        return webexId;
    }

    public void setWebexId(String webexId) {
        this.webexId = webexId;
    }

    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        this.enterpriseCode = enterpriseCode;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getInitialPassword() {
        return initialPassword;
    }

    public void setInitialPassword(String initialPassword) {
        this.initialPassword = initialPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
