package com.kt.api.bean.rate;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/4/28.
 */
public class PstnRates4Put {
    @NotBlank(message = "pstnrate.id.NotBlank")
    private String pid;

    @Valid
    private List<PstnRate> rates;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<PstnRate> getRates() {
        return rates;
    }

    public void setRates(List<PstnRate> rates) {
        this.rates = rates;
    }
}
