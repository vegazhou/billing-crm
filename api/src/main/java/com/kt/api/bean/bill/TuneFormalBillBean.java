package com.kt.api.bean.bill;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Vega Zhou on 2016/5/4.
 */
public class TuneFormalBillBean {

    private Long id;

    private Float amount;

    @NotBlank(message = "bill.formal.tune.comments.NotBlank")
    private String comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
