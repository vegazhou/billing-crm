package wang.huaichao.data.entity.crm;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 8/22/2016.
 */
@Entity
@Table(name = "pstn_charges")
@DynamicInsert
@DynamicUpdate
@IdClass(PstnChargePK.class)
public class PstnCharge {
    private String customerId;
    private String siteName;
    @Id
    private int billingPeriod;
    @Id
    private String orderId;
    private int uncoverdMinutes;
    private BigDecimal cost;
    private Date createdAt;

    public int getBillingPeriod() {
        return billingPeriod;
    }

    public void setBillingPeriod(int billingPeriod) {
        this.billingPeriod = billingPeriod;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
