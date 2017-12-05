package com.kt.api.bean.account;

import com.kt.validation.annotation.InOneOf;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Vega Zhou on 2016/5/11.
 */
public class DepositForm {
    @NotBlank(message = "account.deposit.customerId.NotBlank")
    private String customerId;

    @InOneOf(candidates = {"PREPAID", "DEPOSIT", "CC_DEPOSIT"}, message = "account.deposit.accountType.InOneOf")
    private String accountType;

    private String accountPeriod;

    private Float amount;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(String accountPeriod) {
        this.accountPeriod = accountPeriod;
    }
}
