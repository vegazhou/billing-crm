package com.kt.spalgorithm.model.payload;

import com.kt.spalgorithm.types.LicenseModel;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public class ConferencePayload {
    private String serviceName;
    private int licenseVolume;
    private boolean licenseOverage;
    private LicenseModel licenseModel;
    private int attendeeCapacity;
    private boolean attendeeOverage;

    public ConferencePayload deepClone() {
        ConferencePayload clone = new ConferencePayload();
        clone.serviceName = serviceName;
        clone.licenseModel = licenseModel;
        clone.licenseVolume = licenseVolume;
        clone.licenseOverage = licenseOverage;
        clone.attendeeCapacity = attendeeCapacity;
        clone.attendeeOverage = attendeeOverage;
        return clone;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getLicenseVolume() {
        return licenseVolume;
    }

    public void setLicenseVolume(int licenseVolume) {
        this.licenseVolume = licenseVolume;
    }

    public boolean isLicenseOverage() {
        return licenseOverage;
    }

    public void setLicenseOverage(boolean licenseOverage) {
        this.licenseOverage = licenseOverage;
    }

    public LicenseModel getLicenseModel() {
        return licenseModel;
    }

    public void setLicenseModel(LicenseModel licenseModel) {
        this.licenseModel = licenseModel;
    }

    public int getAttendeeCapacity() {
        return attendeeCapacity;
    }

    public void setAttendeeCapacity(int attendeeCapacity) {
        this.attendeeCapacity = attendeeCapacity;
    }

    public boolean isAttendeeOverage() {
        return attendeeOverage;
    }

    public void setAttendeeOverage(boolean attendeeOverage) {
        this.attendeeOverage = attendeeOverage;
    }

    public boolean isEnabled() {
        return getLicenseModel() != LicenseModel.HOSTS || getLicenseVolume() > 0;
    }
}
