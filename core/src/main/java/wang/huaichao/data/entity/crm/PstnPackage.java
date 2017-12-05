package wang.huaichao.data.entity.crm;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 8/22/2016.
 */
@Entity
@Table(name = "pstn_packages")
@DynamicInsert(value = true)
@DynamicUpdate(value = true)
public class PstnPackage {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(strategy = "uuid", name = "system-uuid")
    private String id;
    private String orderId;
    private Date startDate;
    private Date endDate;
    private int totalMinutes;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(insertable = false, updatable = false)
    private Date createdAt;

    private int leftMinutes;
    @Column(name = "july_2016_left_minutes")
    private Integer july2016LeftMinutes;
    private Integer firstBillingPeriod;
    private Integer lastBillingPeriod;
    private String siteName;
    private String customerId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id", name = "packageId", insertable = false, updatable = false)
    private List<PstnPackageUsage> usages = new ArrayList<>();


    //=================================================================
//    public int getAvailableMinutes() {
//        int used = 0;
//        for (PstnPackageUsage usage : usages) {
//            used += usage.getUsedMinutes();
//        }
//        return totalMinutes - used;
//    }

    public int GetHistoryUsageByBillingPeriod(int billingPeriod) {
        for (PstnPackageUsage usage : usages) {
            if (usage.getBillingPeriod() == billingPeriod)
                return usage.getUsedMinutes();
        }
        throw new RuntimeException("no usage found for period: " + billingPeriod + ", " + orderId);
    }
    //=================================================================


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(int totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<PstnPackageUsage> getUsages() {
        return usages;
    }

    public void setUsages(List<PstnPackageUsage> usages) {
        this.usages = usages;
    }

    public int getLeftMinutes() {
        return leftMinutes;
    }

    public void setLeftMinutes(int leftMinutes) {
        this.leftMinutes = leftMinutes;
    }

    public Integer getFirstBillingPeriod() {
        return firstBillingPeriod;
    }

    public void setFirstBillingPeriod(Integer firstBillingPeriod) {
        this.firstBillingPeriod = firstBillingPeriod;
    }

    public Integer getLastBillingPeriod() {
        return lastBillingPeriod;
    }

    public void setLastBillingPeriod(Integer lastBillingPeriod) {
        this.lastBillingPeriod = lastBillingPeriod;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getJuly2016LeftMinutes() {
        return july2016LeftMinutes;
    }

    public void setJuly2016LeftMinutes(int july2016LeftMinutes) {
        this.july2016LeftMinutes = july2016LeftMinutes;
    }
}
