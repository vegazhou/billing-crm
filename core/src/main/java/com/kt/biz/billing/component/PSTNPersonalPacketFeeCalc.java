package com.kt.biz.billing.component;

import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.billing.BillItem;
import com.kt.biz.billing.FeeCalculator;
import com.kt.biz.model.charge.impl.PSTNPersonalPacket;
import com.kt.biz.model.order.OrderBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Vega Zhou on 2017/5/9.
 */
public class PSTNPersonalPacketFeeCalc extends FeeCalculator<PSTNPersonalPacket> {
    public PSTNPersonalPacketFeeCalc(OrderBean order, PSTNPersonalPacket chargeScheme) {
        super(order, chargeScheme);
    }

    @Override
    public List<BillItem> calculatePrepaidDue(AccountPeriod accountPeriod) {
        //语音叠加包不产生周期性预付费用
        return null;
    }

    @Override
    public List<BillItem> calculatePostpaidDue(AccountPeriod accountPeriod) {
        //语音叠加包不产生此类费用
        return null;
    }

    @Override
    public BillItem calculateRefund(OrderBean order, Date terminatedOn) {
        //语音叠加包提前中止不产生金额减免
        return null;
    }

    @Override
    protected BigDecimal monthlyDue() {
        return new BigDecimal(0);
    }
}
