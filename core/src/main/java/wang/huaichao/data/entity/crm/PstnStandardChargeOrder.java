package wang.huaichao.data.entity.crm;

import wang.huaichao.data.entity.PriceList;

import java.util.Date;

/**
 * Created by Administrator on 8/17/2016.
 */
public class PstnStandardChargeOrder {
    private String orderId;
    private String customerId;
    private String chargeSchemaId;

    private Date startDate;
    private Date endDate;

    private String siteName;
    private String pstnRatesId;

    private PriceList priceList;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getChargeSchemaId() {
        return chargeSchemaId;
    }

    public void setChargeSchemaId(String chargeSchemaId) {
        this.chargeSchemaId = chargeSchemaId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getPstnRatesId() {
        return pstnRatesId;
    }

    public void setPstnRatesId(String pstnRatesId) {
        this.pstnRatesId = pstnRatesId;
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
}
