package com.kt.spalgorithm.util;

import com.kt.biz.types.Language;
import com.kt.biz.types.Location;
import com.kt.biz.types.TimeZone;
import com.kt.entity.mysql.crm.WebExSite;
import com.kt.biz.site.LanguageMatrix;
import com.kt.spalgorithm.model.payload.*;
import com.kt.spalgorithm.types.LicenseModel;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Vega Zhou on 2016/3/28.
 */
public class Entity2Payload {

    public static WbxPayload transform(WebExSite site) {
        WbxPayload payload = new WbxPayload();
        payload.setSitePayload(createSitePayload(site));
        payload.setAudioPayload(createAudioPayload(site));
        payload.setStoragePayload(createStoragePayload(site));

        payload.setMcPayload(createMcPayload(site));
        payload.setEcPayload(createEcPayload(site));
        payload.setTcPayload(createTcPayload(site));
        payload.setScPayload(createScPayload(site));
        payload.setEePayload(createEePayload(site));
        return payload;
    }


    private static SitePayload createSitePayload(WebExSite site) {
        SitePayload payload = new SitePayload();
        if (StringUtils.isNotBlank(site.getTimeZone())) {
            payload.setTimeZone(TimeZone.valueOf(site.getTimeZone()));
        } else {
            payload.setTimeZone(TimeZone.BEIJING);
        }

        if (StringUtils.isNotBlank(site.getCountryCode())) {
            payload.setCountryCode(site.getCountryCode());
        } else {
            payload.setCountryCode("CN");
        }

        payload.setLocation(Location.valueOf(site.getLocation()));

        LanguageMatrix languageMatrix = new LanguageMatrix();
        if (StringUtils.isNotBlank(site.getPrimaryLanguage())) {
            languageMatrix.setPrimaryLanguage(Language.valueOf(site.getPrimaryLanguage()));
        } else {
            languageMatrix.setPrimaryLanguage(Language.SIMPLIFIED_CHINESE);
        }

        String additionalLanguages = site.getAdditionalLanguage();
        if (StringUtils.isNotBlank(additionalLanguages)) {
            for (String additionalLanguage : additionalLanguages.split(";")) {
                languageMatrix.enable(Language.valueOf(additionalLanguage));
            }
        }
        payload.setLanguages(languageMatrix);

        return payload;
    }

    private static AudioPayload createAudioPayload(WebExSite site) {
        AudioPayload payload = new AudioPayload();
        payload.setIsCallInGlobalEnabled(site.getGlobalCallIn() != 0);
        payload.setIsCallInTollEnabled(site.getTollCallIn() != 0);
        payload.setIsCallInTollFreeEnabled(site.getTollFreeCallIn() != 0);
        payload.setIsCallBackEnabled(site.getCallBack() != 0);
        payload.setIsCallBackInternationalEnabled(site.getInternationalCallBack() != 0);

        payload.setIsAudioBroadCastEnabled(site.getAudioBroadCast() != 0);
        payload.setIsCloudConnectedAudioEnabled(site.getCloudConnectedAudio() != 0);
        payload.setIsSipInOut(site.getSipInOut() != 0);
        payload.setIsVoipEnabled(site.getVoip() != 0);
        return payload;
    }


    private static StoragePayload createStoragePayload(WebExSite site) {
        StoragePayload payload = new StoragePayload();
        payload.setStorageCapacity(site.getStorageCapacity() > 0 ? site.getStorageCapacity() : 1);
        payload.setStorageOverage(site.getStorageOverage() != 0);
        return payload;
    }

    private static ConferencePayload createMcPayload(WebExSite site) {
        if (StringUtils.isBlank(site.getMcLicenseModel())) {
            return null;
        }
        ConferencePayload payload = new ConferencePayload();
        payload.setLicenseModel(LicenseModel.valueOf(site.getMcLicenseModel()));
        payload.setLicenseVolume(site.getMcLicenseVolume());
        payload.setLicenseOverage(site.getMcLicenseOverage() != 0);
        payload.setAttendeeCapacity(site.getMcAttendeeCapacity());
        payload.setAttendeeOverage(site.getMcAttendeeOverage() != 0);
        return payload;
    }

    private static ConferencePayload createEcPayload(WebExSite site) {
        if (StringUtils.isBlank(site.getEcLicenseModel())) {
            return null;
        }
        ConferencePayload payload = new ConferencePayload();
        payload.setLicenseModel(LicenseModel.valueOf(site.getEcLicenseModel()));
        payload.setLicenseVolume(site.getEcLicenseVolume());
        payload.setLicenseOverage(site.getEcLicenseOverage() != 0);
        payload.setAttendeeCapacity(site.getEcAttendeeCapacity());
        payload.setAttendeeOverage(site.getEcAttendeeOverage() != 0);
        return payload;
    }

    private static ConferencePayload createTcPayload(WebExSite site) {
        if (StringUtils.isBlank(site.getTcLicenseModel())) {
            return null;
        }
        ConferencePayload payload = new ConferencePayload();
        payload.setLicenseModel(LicenseModel.valueOf(site.getTcLicenseModel()));
        payload.setLicenseVolume(site.getTcLicenseVolume());
        payload.setLicenseOverage(site.getTcLicenseOverage() != 0);
        payload.setAttendeeCapacity(site.getTcAttendeeCapacity());
        payload.setAttendeeOverage(site.getTcAttendeeOverage() != 0);
        return payload;
    }

    private static ConferencePayload createScPayload(WebExSite site) {
        if (StringUtils.isBlank(site.getScLicenseModel())) {
            return null;
        }
        ConferencePayload payload = new ConferencePayload();
        payload.setLicenseModel(LicenseModel.valueOf(site.getScLicenseModel()));
        payload.setLicenseVolume(site.getScLicenseVolume());
        payload.setLicenseOverage(site.getScLicenseOverage() != 0);
        payload.setAttendeeCapacity(site.getScAttendeeCapacity());
        payload.setAttendeeOverage(site.getScAttendeeOverage() != 0);
        return payload;
    }

    private static ConferencePayload createEePayload(WebExSite site) {
        if (StringUtils.isBlank(site.getEeLicenseModel())) {
            return null;
        }
        ConferencePayload payload = new ConferencePayload();
        payload.setLicenseModel(LicenseModel.valueOf(site.getEeLicenseModel()));
        payload.setLicenseVolume(site.getEeLicenseVolume());
        payload.setLicenseOverage(site.getEeLicenseOverage() != 0);
        payload.setAttendeeCapacity(site.getEeAttendeeCapacity());
        payload.setAttendeeOverage(site.getEeAttendeeOverage() != 0);
        return payload;
    }
}
