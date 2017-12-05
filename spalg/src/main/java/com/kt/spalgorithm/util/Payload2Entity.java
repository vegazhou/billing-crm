package com.kt.spalgorithm.util;

import com.kt.entity.mysql.crm.WebExSite;
import com.kt.biz.site.LanguageMatrix;
import com.kt.spalgorithm.model.payload.*;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Vega Zhou on 2016/3/28.
 */
public class Payload2Entity {

    public static WebExSite transform(WbxPayload payload) {
        WebExSite entity = new WebExSite();
        fillWithSitePayload(entity, payload.getSitePayload());
        fillWithAudioPayload(entity, payload.getAudioPayload());
        fillWithStoragePayload(entity, payload.getStoragePayload());

        fillWithMcPayload(entity, payload.getMcPayload());
        fillWithEcPayload(entity, payload.getEcPayload());
        fillWithTcPayload(entity, payload.getTcPayload());
        fillWithScPayload(entity, payload.getScPayload());
        fillWithEePayload(entity, payload.getEePayload());

        return entity;
    }

    private static void fillWithSitePayload(WebExSite entity, SitePayload payload) {
        if (payload == null) {
            return;
        }

        entity.setCountryCode(payload.getCountryCode());
        if (payload.getTimeZone() != null) {
            entity.setTimeZone(payload.getTimeZone().toString());
        }
        if (payload.getLocation() != null) {
            entity.setLocation(payload.getLocation().toString());
        }
        LanguageMatrix languageMatrix = payload.getLanguages();
        if (languageMatrix != null) {
            if (languageMatrix.getPrimaryLanguage() != null) {
                entity.setPrimaryLanguage(languageMatrix.getPrimaryLanguage().toString());
            }
            entity.setAdditionalLanguage(StringUtils.join(languageMatrix.getEnabledAdditionalLanguages(), ";"));
        }
    }


    private static void fillWithAudioPayload(WebExSite entity, AudioPayload payload) {
        if (payload == null) {
            return;
        }

        entity.setCallBack(payload.isCallBackEnabled() ? 1 : 0);
        entity.setInternationalCallBack(payload.isCallBackInternationalEnabled() ? 1 : 0);
        entity.setGlobalCallIn(payload.isCallInGlobalEnabled() ?  1 : 0);
        entity.setTollCallIn(payload.isCallInTollEnabled() ?  1 : 0);
        entity.setTollFreeCallIn(payload.isCallInTollFreeEnabled() ? 1 : 0);

        entity.setAudioBroadCast(payload.isAudioBroadCastEnabled() ? 1 : 0);
        entity.setCloudConnectedAudio(payload.isCloudConnectedAudioEnabled() ? 1 : 0);
        entity.setVoip(payload.isVoipEnabled() ? 1 : 0);
        entity.setSipInOut(payload.isSipInOut() ? 1 : 0);
    }


    private static void fillWithStoragePayload(WebExSite entity, StoragePayload payload) {
        if (payload == null) {
            return;
        }

        entity.setStorageCapacity(payload.getStorageCapacity());
        entity.setStorageOverage(payload.isStorageOverage() ? 1 : 0);
    }

    private static void fillWithMcPayload(WebExSite entity, ConferencePayload payload) {
        if (payload == null) {
            return;
        }

        if (payload.getLicenseModel() != null) {
            entity.setMcLicenseModel(payload.getLicenseModel().toString());
        }
        entity.setMcLicenseVolume(payload.getLicenseVolume());
        entity.setMcLicenseOverage(payload.isLicenseOverage() ? 1 : 0);
        entity.setMcAttendeeCapacity(payload.getAttendeeCapacity());
        entity.setMcAttendeeOverage(payload.isAttendeeOverage() ? 1 : 0);
    }


    private static void fillWithEcPayload(WebExSite entity, ConferencePayload payload) {
        if (payload == null) {
            return;
        }

        if (payload.getLicenseModel() != null) {
            entity.setEcLicenseModel(payload.getLicenseModel().toString());
        }
        entity.setEcLicenseVolume(payload.getLicenseVolume());
        entity.setEcLicenseOverage(payload.isLicenseOverage() ? 1 : 0);
        entity.setEcAttendeeCapacity(payload.getAttendeeCapacity());
        entity.setEcAttendeeOverage(payload.isAttendeeOverage() ? 1 : 0);
    }

    private static void fillWithTcPayload(WebExSite entity, ConferencePayload payload) {
        if (payload == null) {
            return;
        }

        if (payload.getLicenseModel() != null) {
            entity.setTcLicenseModel(payload.getLicenseModel().toString());
        }
        entity.setTcLicenseVolume(payload.getLicenseVolume());
        entity.setTcLicenseOverage(payload.isLicenseOverage() ? 1 : 0);
        entity.setTcAttendeeCapacity(payload.getAttendeeCapacity());
        entity.setTcAttendeeOverage(payload.isAttendeeOverage() ? 1 : 0);
    }

    private static void fillWithScPayload(WebExSite entity, ConferencePayload payload) {
        if (payload == null) {
            return;
        }

        if (payload.getLicenseModel() != null) {
            entity.setScLicenseModel(payload.getLicenseModel().toString());
        }
        entity.setScLicenseVolume(payload.getLicenseVolume());
        entity.setScLicenseOverage(payload.isLicenseOverage() ? 1 : 0);
        entity.setScAttendeeCapacity(payload.getAttendeeCapacity());
        entity.setScAttendeeOverage(payload.isAttendeeOverage() ? 1 : 0);
    }


    private static void fillWithEePayload(WebExSite entity, ConferencePayload payload) {
        if (payload == null) {
            return;
        }

        if (payload.getLicenseModel() != null) {
            entity.setEeLicenseModel(payload.getLicenseModel().toString());
        }
        entity.setEeLicenseVolume(payload.getLicenseVolume());
        entity.setEeLicenseOverage(payload.isLicenseOverage() ? 1 : 0);
        entity.setEeAttendeeCapacity(payload.getAttendeeCapacity());
        entity.setEeAttendeeOverage(payload.isAttendeeOverage() ? 1 : 0);
    }
}
