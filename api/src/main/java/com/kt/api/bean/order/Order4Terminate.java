package com.kt.api.bean.order;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Vega Zhou on 2016/4/29.
 */
public class Order4Terminate {
    @NotBlank(message = "order.terminate.type.NotBlank")
    private String type;

    @NotBlank(message = "order.terminate.date.NotBlank")
    private String date;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
