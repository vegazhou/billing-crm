package wang.huaichao.data.entity.crm;


import java.math.BigDecimal;
import java.util.Date;

public class PortChargeSchema {
    private String siteName;
    private int ports;
    private BigDecimal price;
    private BigDecimal overflowPrice;
    private Date startDate;
    private Date endDate;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public int getPorts() {
        return ports;
    }

    public void setPorts(int ports) {
        this.ports = ports;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public BigDecimal getOverflowPrice() {
        return overflowPrice;
    }

    public void setOverflowPrice(BigDecimal overflowPrice) {
        this.overflowPrice = overflowPrice;
    }
}
