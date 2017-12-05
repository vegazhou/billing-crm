package com.kt.biz.bean;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/30.
 */
public interface PSTNStandardChargeBean {

    void setCommonSites(List<String> value);

    List<String> getCommonSites();

    void setEffectiveBefore(String value);

    String getEffectiveBefore();
}
