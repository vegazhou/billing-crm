package com.kt.biz.billing.component;

import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.billing.BillItem;
import com.kt.biz.billing.FeeCalculator;
import com.kt.biz.model.charge.impl.WebExECPayPerUse;
import com.kt.biz.model.order.OrderBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/4/12.
 */
public class WebExECPayPerUseFeeCalc extends FeeCalculator<WebExECPayPerUse> {
    public WebExECPayPerUseFeeCalc(OrderBean order, WebExECPayPerUse chargeScheme) {
        super(order, chargeScheme);
    }

    @Override
    public List<BillItem> calculatePrepaidDue(AccountPeriod accountPeriod) {
        // EC按次购买产品不产生周期性预付费用
        return null;
    }

    @Override
    public List<BillItem> calculatePostpaidDue(AccountPeriod accountPeriod) {
        //TODO: 计算当月发生的EC按次付款费用
        return null;
    }

    @Override
    public BillItem calculateRefund(OrderBean order, Date terminatedOn) {
        //EC PPU有效期提前中止，不发生退款
        return null;
    }

    @Override
    protected BigDecimal monthlyDue() {
        return new BigDecimal(0);
    }
}
