package wang.huaichao.data.ds;

import wang.huaichao.data.entity.PriceList;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 8/18/2016.
 */
public class ChargeScheme {
    private String orderId;
    private Date startDate;
    private Date endDate;
    private PriceList priceList;
    private int uncoverdMinutes = 0;
    private BigDecimal cost = BigDecimal.ZERO;

    // ==============================================================

    public void accumulateUncoverdMinutes(int uncoverdMinutes) {
        this.uncoverdMinutes += uncoverdMinutes;
    }

    public void accumulateCost(BigDecimal cost) {
        this.cost = this.cost.add(cost);
    }

    // ==============================================================

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public PriceList getPriceList() {
        return priceList;
    }

    public void setPriceList(PriceList priceList) {
        this.priceList = priceList;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getUncoverdMinutes() {
        return uncoverdMinutes;
    }

    public void setUncoverdMinutes(int uncoverdMinutes) {
        this.uncoverdMinutes = uncoverdMinutes;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
