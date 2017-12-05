package com.kt.biz.billing.component;

import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.billing.BillItem;
import com.kt.biz.billing.FeeCalculator;
import com.kt.biz.model.charge.impl.CcCallCenterPstnCharge;
import com.kt.biz.model.order.OrderBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Vega on 2017/10/10.
 */
public class CcCallCenterPstnChargeFeeCalc extends FeeCalculator<CcCallCenterPstnCharge> {

    public CcCallCenterPstnChargeFeeCalc(OrderBean order, CcCallCenterPstnCharge chargeScheme) {
        super(order, chargeScheme);
    }

    @Override
    public List<BillItem> calculatePrepaidDue(AccountPeriod accountPeriod) {
        return null;
    }

    @Override
    public List<BillItem> calculatePostpaidDue(AccountPeriod accountPeriod) {
        return null;
    }

    @Override
    protected BigDecimal monthlyDue() {
        return new BigDecimal(0);
    }

    @Override
    public BillItem calculateRefund(OrderBean originalOrder, Date terminatedOn) {
        return null;
    }
}
