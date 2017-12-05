package wang.huaichao.data.entity.crm;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 8/24/2016.
 */
@Entity
@Table(name = "meeting_records")
@DynamicInsert
@DynamicUpdate
public class MeetingRecord implements Comparable<MeetingRecord> {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(strategy = "uuid", name = "system-uuid")
    private String id;
    private int billingPeriod;
    private long confId;             // NUMBER(19, 0) PRIMARY KEY,
    private String confName;         // VARCHAR2(512),
    private String customerId;
    private String siteName;         // VARCHAR2(128),
    private String userName;         // VARCHAR2(1024)
    private String hostName;         // VARCHAR2(1024),
    private Date startTime;          // DATE,
    private Date endTime;            // DATE,
    private String userNumber;       // VARCHAR(32),
    private String accessNumber;     // VARCHAR(32),
    @Type(type = "yes_no")
    private boolean isCallIn;        // VARCHAR(1),
    @Type(type = "yes_no")
    private boolean isInternational; // VARCHAR(1),
    private String orderId;

    private String accessType;

    private int duration;            // INTEGER,
    private int coverdMinutes;       // INTEGER,
    private int uncoverdMinutes;     // INTEGER,
    private BigDecimal price;        // FLOAT,
    private BigDecimal cost;         // FLOAT

    @Transient
    private String pstnCode;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBillingPeriod() {
        return billingPeriod;
    }

    public void setBillingPeriod(int billingPeriod) {
        this.billingPeriod = billingPeriod;
    }

    public void setConfId(long confId) {
        this.confId = confId;
    }

    public long getConfId() {
        return confId;
    }

    public void setConfId(Long confId) {
        this.confId = confId;
    }

    public String getConfName() {
        return confName;
    }

    public void setConfName(String confName) {
        this.confName = confName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getAccessNumber() {
        return accessNumber;
    }

    public void setAccessNumber(String accessNumber) {
        this.accessNumber = accessNumber;
    }

    public boolean isCallIn() {
        return isCallIn;
    }

    public void setCallIn(boolean callIn) {
        isCallIn = callIn;
    }

    public boolean isInternational() {
        return isInternational;
    }

    public void setInternational(boolean international) {
        isInternational = international;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCoverdMinutes() {
        return coverdMinutes;
    }

    public void setCoverdMinutes(int coverdMinutes) {
        this.coverdMinutes = coverdMinutes;
    }

    public int getUncoverdMinutes() {
        return uncoverdMinutes;
    }

    public void setUncoverdMinutes(int uncoverdMinutes) {
        this.uncoverdMinutes = uncoverdMinutes;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getPstnCode() {
        return pstnCode;
    }

    public void setPstnCode(String pstnCode) {
        this.pstnCode = pstnCode;
    }

    @Override
    public int compareTo(MeetingRecord o) {
        return Long.compare(getStartTime().getTime(), o.getStartTime().getTime());
    }
}
