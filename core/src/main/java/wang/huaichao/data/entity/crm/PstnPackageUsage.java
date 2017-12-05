package wang.huaichao.data.entity.crm;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 8/22/2016.
 */
@Entity
@Table(name = "pstn_package_usages")
@DynamicInsert
@DynamicUpdate
@IdClass(PstnPackageUsagePK.class)
public class PstnPackageUsage {
    @Id
    private String packageId;
    @Id
    private int billingPeriod;
    private int usedMinutes;
    private Date createdAt;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public int getBillingPeriod() {
        return billingPeriod;
    }

    public void setBillingPeriod(int billingPeriod) {
        this.billingPeriod = billingPeriod;
    }

    public int getUsedMinutes() {
        return usedMinutes;
    }

    public void setUsedMinutes(int usedMinutes) {
        this.usedMinutes = usedMinutes;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
