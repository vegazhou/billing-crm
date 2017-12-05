package com.kt.biz.bean;

import com.kt.biz.types.BizType;

/**
 * Created by Administrator on 2016/6/15.
 */
public class TelecomProductBean {

    private String productionId;
    private BizType bizType;
    private int attendeeCapacity;

    public String getProductionId() {
        return productionId;
    }

    public void setProductionId(String productionId) {
        this.productionId = productionId;
    }

    public BizType getBizType() {
        return bizType;
    }

    public void setBizType(BizType bizType) {
        this.bizType = bizType;
    }

    public int getAttendeeCapacity() {
        return attendeeCapacity;
    }

    public void setAttendeeCapacity(int attendeeCapacity) {
        this.attendeeCapacity = attendeeCapacity;
    }
}
