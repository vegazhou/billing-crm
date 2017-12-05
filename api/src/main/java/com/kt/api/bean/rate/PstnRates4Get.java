package com.kt.api.bean.rate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/4/28.
 */
public class PstnRates4Get {
    private String pid;

    private List<PstnRate> rates = new ArrayList<PstnRate>(40);

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<PstnRate> getRates() {
        return rates;
    }

    public void add(PstnRate rate) {
        rates.add(rate);
    }
}
