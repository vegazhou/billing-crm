package wang.huaichao.data.entity.crm;

/**
 * Created by Administrator on 8/18/2016.
 */
public class OrderChargeInfo {
    private String orderId;
    private String customerId;
    private String chargeSchemaId;

    private String siteName;
    private String rateId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getRateId() {
        return rateId;
    }

    public void setRateId(String rateId) {
        this.rateId = rateId;
    }
}
