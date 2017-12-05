package wang.huaichao.data.entity.edr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wang.huaichao.data.entity.PriceList;
import wang.huaichao.data.entity.crm.MeetingRecord;
import wang.huaichao.data.service.PstnMetaData;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 8/12/2016.
 */
public class CallDataRecord {
    private static final Logger log = LoggerFactory.getLogger(CallDataRecord.class);

    private String siteName;
    private String hostName;
    private String userName;
    private long confId;
    private String confName;
    private Date startTime;
    private Date endTime;
    private String orignCountryCode;
    private String originAreaCode;
    private String origNumber;
    private String destCountryCode;
    private String destAreaCode;
    private String destNumber;
    private String accessNumber;
    private boolean callIn;
    private String callType;
    private String orderId;


    // ============================================================
    // used while billing
    // ============================================================
    private String userNumber;
    private String platformNumber;
    private boolean internationalCall;
    private String accessType;
    private int duration = -1;
    private int coveredMinutes = -1;
    private int uncoverdMinutes = -1;
    private BigDecimal rate = BigDecimal.ZERO;
    private BigDecimal cost = BigDecimal.ZERO;

    public void calculate() {
        calculateDuration();
        calculateNumber();
        calculateIsInternationalCall();
        calculateAccessType();
    }

    public void calculateDuration() {
        try {
            duration = (int) (getEndTime().getTime() - getStartTime().getTime() + 59999) / 60000;
        } catch (Exception e) {
            log.error("calculating duration error", e);
            duration = 0;
        }
    }

    public void calculateIsInternationalCall() {
        if (callType != null) {
            internationalCall = callType.equals("International");
        } else {
            if (callIn) {
                // fixme: ?
                internationalCall = "86".equals(orignCountryCode) == false;
            } else {
                internationalCall = "86".equals(destCountryCode) == false;
            }
        }
    }

    public void calculateNumber() {
        String onumber = ((orignCountryCode == null ? "" : orignCountryCode) +
            (originAreaCode == null ? "" : originAreaCode) + origNumber);
        String dnumber = ((destCountryCode == null ? "" : destCountryCode) +
            (destAreaCode == null ? "" : destAreaCode) + destNumber);

        if (callIn) {
            userNumber = onumber;
        } else {
            userNumber = dnumber;
        }

        if (accessNumber != null) {
            platformNumber = accessNumber;
        } else {
            if (callIn) {
                platformNumber = dnumber;
            } else {
                platformNumber = onumber;
            }
        }

        if (!platformNumber.startsWith("+")) {
            platformNumber = "+" + platformNumber;
        }
    }

    public void calculateAccessType() {
        if (callIn) {
            if (internationalCall) {
                accessType = PstnMetaData.CallInAccessNumber2FeeNameMap.get(platformNumber);
            } else {
                if (PstnMetaData.CallIn400Map.containsKey(platformNumber)) {
                    accessType = "国内400呼入";
                } else {
                    accessType = "国内本地呼入";
                }
            }
        } else {
            if (internationalCall) {
                int zone = PstnMetaData.CountryAreaCode2ZoneMap.get(
                    (destCountryCode == null ? "" : destCountryCode) +
                        (destAreaCode == null ? "" : destAreaCode)
                );
                accessType = PstnMetaData.Zone2ZoneNameMap.get(zone);
            } else {
                accessType = "国内本地呼出";
            }
        }
    }


    // ============================================================
    // getters & setters
    // ============================================================

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(String platformNumber) {
        this.platformNumber = platformNumber;
    }

    public boolean isInternationalCall() {
        return internationalCall;
    }

    public void setInternationalCall(boolean internationalCall) {
        this.internationalCall = internationalCall;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
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

    public long getConfId() {
        return confId;
    }

    public void setConfId(long confId) {
        this.confId = confId;
    }

    public String getConfName() {
        return confName;
    }

    public void setConfName(String confName) {
        this.confName = confName;
    }

    public Date getStartTime() {
        return startTime;
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

    public String getOrignCountryCode() {
        return orignCountryCode;
    }

    public void setOrignCountryCode(String orignCountryCode) {
        this.orignCountryCode = orignCountryCode;
    }

    public String getOriginAreaCode() {
        return originAreaCode;
    }

    public void setOriginAreaCode(String originAreaCode) {
        this.originAreaCode = originAreaCode;
    }

    public String getOrigNumber() {
        return origNumber;
    }

    public void setOrigNumber(String origNumber) {
        this.origNumber = origNumber;
    }

    public String getDestCountryCode() {
        return destCountryCode;
    }

    public void setDestCountryCode(String destCountryCode) {
        this.destCountryCode = destCountryCode;
    }

    public String getDestAreaCode() {
        return destAreaCode;
    }

    public void setDestAreaCode(String destAreaCode) {
        this.destAreaCode = destAreaCode;
    }

    public String getDestNumber() {
        return destNumber;
    }

    public void setDestNumber(String destNumber) {
        this.destNumber = destNumber;
    }

    public String getAccessNumber() {
        return accessNumber;
    }

    public void setAccessNumber(String accessNumber) {
        this.accessNumber = accessNumber;
    }

    public boolean isCallIn() {
        return callIn;
    }

    public void setCallIn(boolean callIn) {
        this.callIn = callIn;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCoveredMinutes() {
        return coveredMinutes;
    }

    public void setCoveredMinutes(int coveredMinutes) {
        this.coveredMinutes = coveredMinutes;
    }

    public int getUncoverdMinutes() {
        return uncoverdMinutes;
    }

    public void setUncoverdMinutes(int uncoverdMinutes) {
        this.uncoverdMinutes = uncoverdMinutes;
        this.coveredMinutes = duration - uncoverdMinutes;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
        this.cost = rate.multiply(new BigDecimal(getUncoverdMinutes()));
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
