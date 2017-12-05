package wang.huaichao.data.ds;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 9/8/2016.
 */
public class ProductMonthlyFee {
    private String orderId;
    private String productName;
    private int hosts = 0;
    private int ports = 0;
    private String siteName;
    private BigDecimal price = BigDecimal.ZERO;
    private Date startDate;
    private Date endDate;

    public BigDecimal getCost() {
        return price.multiply(new BigDecimal(hosts + ports));
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getHosts() {
        return hosts;
    }

    public void setHosts(int hosts) {
        this.hosts = hosts;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getPorts() {
        return ports;
    }

    public void setPorts(int ports) {
        this.ports = ports;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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
