package com.kt.api.bean.bill;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/5/9.
 */
public class TempBillConfirmRequest {
    @Valid
    private List<TempBillConfirmBean> confirmations;

    public List<TempBillConfirmBean> getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(List<TempBillConfirmBean> confirmations) {
        this.confirmations = confirmations;
    }
}
