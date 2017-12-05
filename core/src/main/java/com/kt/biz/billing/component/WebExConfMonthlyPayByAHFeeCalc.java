package com.kt.biz.billing.component;

import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.billing.BillItem;
import com.kt.biz.billing.FeeCalculator;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayByAH;
import com.kt.biz.model.order.OrderBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/10/10.
 */
public class WebExConfMonthlyPayByAHFeeCalc extends FeeCalculator<WebExConfMonthlyPayByAH> {

    public WebExConfMonthlyPayByAHFeeCalc(OrderBean order, WebExConfMonthlyPayByAH chargeScheme) {
        super(order, chargeScheme);
    }

    @Override
    public List<BillItem> calculatePrepaidDue(AccountPeriod accountPeriod) {
        //按激活用户数购买产品不发生此类费用
        return null;
    }

    @Override
    public List<BillItem> calculatePostpaidDue(AccountPeriod accountPeriod) {
        //TODO: 调用接口查询激活用户数
        return null;
    }

    @Override
    protected BigDecimal monthlyDue() {
        return new BigDecimal(0);
    }
}
