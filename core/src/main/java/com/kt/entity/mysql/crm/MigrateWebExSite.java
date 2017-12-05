package com.kt.entity.mysql.crm;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
@Table(name = "MIGRATE_B_WEBEX_SITES")
@Entity
public class MigrateWebExSite {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "PID")
    private String pid;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "SITENAME")
    private String siteName;

    //1.PROVISIONING  2.IN_EFFECT  3.TERMINATING  4: SUSPENDED
    @Column(name = "STATE")
    private String state;

    //PRIMARY FIELDS
    @Column(name = "PRIMARY_LANGUAGE")
    private String primaryLanguage;
    @Column(name = "ADDITIONAL_LANGUAGES")
    private String additionalLanguage;
    @Column(name = "TIMEZONE")
    private String timeZone;
    @Column(name = "COUNTRY_CODE")
    private String countryCode;
    @Column(name = "LOCATION")
    private String location;

    //AUDIO RELATED FIELDS
    @Column(name = "AUDIO_BROADCAST")
    private int audioBroadCast = 0;
    @Column(name = "CALLBACK")
    private int callBack = 0;
    @Column(name = "INTERNATIONAL_CALLBACK")
    private int internationalCallBack = 0;
    @Column(name = "TOLL_CALLIN")
    private int tollCallIn = 0;
    @Column(name = "TOLL_FREE_CALLIN")
    private int tollFreeCallIn = 0;
    @Column(name = "GLOBAL_CALLIN")
    private int globalCallIn = 0;
    @Column(name = "CLOUD_CONNECTED_AUDIO")
    private int cloudConnectedAudio = 0;
    @Column(name = "VOIP")
    private int voip;
    @Column(name = "SIP_INOUT")
    private int sipInOut = 0;

    //STORAGE RELATED FIELDS
    @Column(name = "STORAGE_OVERAGE")
    private int storageOverage = 0;
    @Column(name = "STORAGE_CAPACITY")
    private int storageCapacity = 0;


    //MC CONFERENCE RELATED FIELDS
    @Column(name = "MC_LICENSE_MODEL")
    private String mcLicenseModel;
    @Column(name = "MC_LICENSE_VOLUME")
    private int mcLicenseVolume = 0;
    @Column(name = "MC_LICENSE_OVERAGE")
    private int mcLicenseOverage = 0;
    @Column(name = "MC_ATTENDEE_CAPACITY")
    private int mcAttendeeCapacity = 0;
    @Column(name = "MC_ATTENDEE_OVERAGE")
    private int mcAttendeeOverage = 0;


    //EC CONFERENCE RELATED FIELDS
    @Column(name = "EC_LICENSE_MODEL")
    private String ecLicenseModel;
    @Column(name = "EC_LICENSE_VOLUME")
    private int ecLicenseVolume = 0;
    @Column(name = "EC_LICENSE_OVERAGE")
    private int ecLicenseOverage = 0;
    @Column(name = "EC_ATTENDEE_CAPACITY")
    private int ecAttendeeCapacity = 0;
    @Column(name = "EC_ATTENDEE_OVERAGE")
    private int ecAttendeeOverage = 0;


    //TC CONFERENCE RELATED FIELDS
    @Column(name = "TC_LICENSE_MODEL")
    private String tcLicenseModel;
    @Column(name = "TC_LICENSE_VOLUME")
    private int tcLicenseVolume = 0;
    @Column(name = "TC_LICENSE_OVERAGE")
    private int tcLicenseOverage = 0;
    @Column(name = "TC_ATTENDEE_CAPACITY")
    private int tcAttendeeCapacity = 0;
    @Column(name = "TC_ATTENDEE_OVERAGE")
    private int tcAttendeeOverage = 0;


    //SC CONFERENCE RELATED FIELDS
    @Column(name = "SC_LICENSE_MODEL")
    private String scLicenseModel;
    @Column(name = "SC_LICENSE_VOLUME")
    private int scLicenseVolume = 0;
    @Column(name = "SC_LICENSE_OVERAGE")
    private int scLicenseOverage = 0;
    @Column(name = "SC_ATTENDEE_CAPACITY")
    private int scAttendeeCapacity = 0;
    @Column(name = "SC_ATTENDEE_OVERAGE")
    private int scAttendeeOverage = 0;


    //EE CONFERENCE RELATED FIELDS
    @Column(name = "EE_LICENSE_MODEL")
    private String eeLicenseModel;
    @Column(name = "EE_LICENSE_VOLUME")
    private int eeLicenseVolume = 0;
    @Column(name = "EE_LICENSE_OVERAGE")
    private int eeLicenseOverage = 0;
    @Column(name = "EE_ATTENDEE_CAPACITY")
    private int eeAttendeeCapacity = 0;
    @Column(name = "EE_ATTENDEE_OVERAGE")
    private int eeAttendeeOverage = 0;
    
    @Column(name = "LASTMODIFIEDDATE")
    private String lastModifiedDate;

    @Column(name = "LASTMODIFIEDBY")
    private String lastModifiedBy;




    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getPrimaryLanguage() {
        return primaryLanguage;
    }

    public void setPrimaryLanguage(String primaryLanguage) {
        this.primaryLanguage = primaryLanguage;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAdditionalLanguage() {
        return additionalLanguage;
    }

    public void setAdditionalLanguage(String additionalLanguage) {
        this.additionalLanguage = additionalLanguage;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAudioBroadCast() {
        return audioBroadCast;
    }

    public void setAudioBroadCast(int audioBroadCast) {
        this.audioBroadCast = audioBroadCast;
    }

    public int getCallBack() {
        return callBack;
    }

    public void setCallBack(int callBack) {
        this.callBack = callBack;
    }

    public int getInternationalCallBack() {
        return internationalCallBack;
    }

    public void setInternationalCallBack(int internationalCallBack) {
        this.internationalCallBack = internationalCallBack;
    }

    public int getTollCallIn() {
        return tollCallIn;
    }

    public void setTollCallIn(int tollCallIn) {
        this.tollCallIn = tollCallIn;
    }

    public int getTollFreeCallIn() {
        return tollFreeCallIn;
    }

    public void setTollFreeCallIn(int tollFreeCallIn) {
        this.tollFreeCallIn = tollFreeCallIn;
    }

    public int getGlobalCallIn() {
        return globalCallIn;
    }

    public void setGlobalCallIn(int globalCallIn) {
        this.globalCallIn = globalCallIn;
    }

    public int getCloudConnectedAudio() {
        return cloudConnectedAudio;
    }

    public void setCloudConnectedAudio(int cloudConnectedAudio) {
        this.cloudConnectedAudio = cloudConnectedAudio;
    }

    public int getVoip() {
        return voip;
    }

    public void setVoip(int voip) {
        this.voip = voip;
    }

    public int getSipInOut() {
        return sipInOut;
    }

    public void setSipInOut(int sipInOut) {
        this.sipInOut = sipInOut;
    }

    public int getStorageOverage() {
        return storageOverage;
    }

    public void setStorageOverage(int storageOverage) {
        this.storageOverage = storageOverage;
    }

    public int getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(int storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    public String getMcLicenseModel() {
        return mcLicenseModel;
    }

    public void setMcLicenseModel(String mcLicenseModel) {
        this.mcLicenseModel = mcLicenseModel;
    }

    public int getMcLicenseVolume() {
        return mcLicenseVolume;
    }

    public void setMcLicenseVolume(int mcLicenseVolume) {
        this.mcLicenseVolume = mcLicenseVolume;
    }

    public int getMcLicenseOverage() {
        return mcLicenseOverage;
    }

    public void setMcLicenseOverage(int mcLicenseOverage) {
        this.mcLicenseOverage = mcLicenseOverage;
    }

    public int getMcAttendeeCapacity() {
        return mcAttendeeCapacity;
    }

    public void setMcAttendeeCapacity(int mcAttendeeCapacity) {
        this.mcAttendeeCapacity = mcAttendeeCapacity;
    }

    public int getMcAttendeeOverage() {
        return mcAttendeeOverage;
    }

    public void setMcAttendeeOverage(int mcAttendeeOverage) {
        this.mcAttendeeOverage = mcAttendeeOverage;
    }

    public String getEcLicenseModel() {
        return ecLicenseModel;
    }

    public void setEcLicenseModel(String ecLicenseModel) {
        this.ecLicenseModel = ecLicenseModel;
    }

    public int getEcLicenseVolume() {
        return ecLicenseVolume;
    }

    public void setEcLicenseVolume(int ecLicenseVolume) {
        this.ecLicenseVolume = ecLicenseVolume;
    }

    public int getEcLicenseOverage() {
        return ecLicenseOverage;
    }

    public void setEcLicenseOverage(int ecLicenseOverage) {
        this.ecLicenseOverage = ecLicenseOverage;
    }

    public int getEcAttendeeCapacity() {
        return ecAttendeeCapacity;
    }

    public void setEcAttendeeCapacity(int ecAttendeeCapacity) {
        this.ecAttendeeCapacity = ecAttendeeCapacity;
    }

    public int getEcAttendeeOverage() {
        return ecAttendeeOverage;
    }

    public void setEcAttendeeOverage(int ecAttendeeOverage) {
        this.ecAttendeeOverage = ecAttendeeOverage;
    }

    public String getTcLicenseModel() {
        return tcLicenseModel;
    }

    public void setTcLicenseModel(String tcLicenseModel) {
        this.tcLicenseModel = tcLicenseModel;
    }

    public int getTcLicenseVolume() {
        return tcLicenseVolume;
    }

    public void setTcLicenseVolume(int tcLicenseVolume) {
        this.tcLicenseVolume = tcLicenseVolume;
    }

    public int getTcLicenseOverage() {
        return tcLicenseOverage;
    }

    public void setTcLicenseOverage(int tcLicenseOverage) {
        this.tcLicenseOverage = tcLicenseOverage;
    }

    public int getTcAttendeeCapacity() {
        return tcAttendeeCapacity;
    }

    public void setTcAttendeeCapacity(int tcAttendeeCapacity) {
        this.tcAttendeeCapacity = tcAttendeeCapacity;
    }

    public int getTcAttendeeOverage() {
        return tcAttendeeOverage;
    }

    public void setTcAttendeeOverage(int tcAttendeeOverage) {
        this.tcAttendeeOverage = tcAttendeeOverage;
    }

    public String getScLicenseModel() {
        return scLicenseModel;
    }

    public void setScLicenseModel(String scLicenseModel) {
        this.scLicenseModel = scLicenseModel;
    }

    public int getScLicenseVolume() {
        return scLicenseVolume;
    }

    public void setScLicenseVolume(int scLicenseVolume) {
        this.scLicenseVolume = scLicenseVolume;
    }

    public int getScLicenseOverage() {
        return scLicenseOverage;
    }

    public void setScLicenseOverage(int scLicenseOverage) {
        this.scLicenseOverage = scLicenseOverage;
    }

    public int getScAttendeeCapacity() {
        return scAttendeeCapacity;
    }

    public void setScAttendeeCapacity(int scAttendeeCapacity) {
        this.scAttendeeCapacity = scAttendeeCapacity;
    }

    public int getScAttendeeOverage() {
        return scAttendeeOverage;
    }

    public void setScAttendeeOverage(int scAttendeeOverage) {
        this.scAttendeeOverage = scAttendeeOverage;
    }

    public String getEeLicenseModel() {
        return eeLicenseModel;
    }

    public void setEeLicenseModel(String eeLicenseModel) {
        this.eeLicenseModel = eeLicenseModel;
    }

    public int getEeLicenseVolume() {
        return eeLicenseVolume;
    }

    public void setEeLicenseVolume(int eeLicenseVolume) {
        this.eeLicenseVolume = eeLicenseVolume;
    }

    public int getEeLicenseOverage() {
        return eeLicenseOverage;
    }

    public void setEeLicenseOverage(int eeLicenseOverage) {
        this.eeLicenseOverage = eeLicenseOverage;
    }

    public int getEeAttendeeCapacity() {
        return eeAttendeeCapacity;
    }

    public void setEeAttendeeCapacity(int eeAttendeeCapacity) {
        this.eeAttendeeCapacity = eeAttendeeCapacity;
    }

    public int getEeAttendeeOverage() {
        return eeAttendeeOverage;
    }

    public void setEeAttendeeOverage(int eeAttendeeOverage) {
        this.eeAttendeeOverage = eeAttendeeOverage;
    }

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
    
    
}
