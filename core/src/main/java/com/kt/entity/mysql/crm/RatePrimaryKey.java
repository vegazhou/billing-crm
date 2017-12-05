package com.kt.entity.mysql.crm;

import java.io.Serializable;

/**
 * Created by Vega Zhou on 2016/3/8.
 */
public class RatePrimaryKey implements Serializable {
    private String pid;

    private String code;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RatePrimaryKey) {
            RatePrimaryKey o = (RatePrimaryKey) obj;
            return pid.equals(o.getPid()) && code.equals(o.getCode());
        } else {
            return false;
        }
    }
}
