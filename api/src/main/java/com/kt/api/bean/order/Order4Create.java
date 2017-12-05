package com.kt.api.bean.order;

import com.kt.validation.annotation.InOneOf;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/19.
 */
public class Order4Create {
    @NotBlank(message = "order.contractId.NotBlank")
    private String contractId;

    private List<String> productIds;

    @NotBlank(message = "order.startDate.NotBlank")
    private String startDate;

    @NotBlank(message = "order.payInterval.NotBlank")
    @InOneOf(candidates = {"MONTHLY", "QUARTERLY", "HALF_YEARLY", "YEARLY", "ONE_TIME"}, message = "order.payInterval.Incorrect")
    private String payInterval;

    private String bizChance;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPayInterval() {
        return payInterval;
    }

    public void setPayInterval(String payInterval) {
        this.payInterval = payInterval;
    }

    public String getBizChance() {
        return bizChance;
    }

    public void setBizChance(String bizChance) {
        this.bizChance = bizChance;
    }
}
