package com.kt.api.bean.order;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Vega Zhou on 2016/9/28.
 */
public class TuneFirstInstallmentForm {
    @NotBlank(message = "order.tunefi.orderId.NotBlank")
    private String orderId;

    private Float amount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}
