package com.kt.spalgorithm.builder;

import com.kt.biz.types.Language;
import com.kt.biz.types.Location;
import com.kt.biz.types.TimeZone;
import com.kt.biz.site.LanguageMatrix;
import com.kt.spalgorithm.model.payload.*;
import com.kt.spalgorithm.types.*;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public class PayloadBuilder {
    WbxPayload payload = new WbxPayload();

    public PayloadBuilder() {
    }

    public void buildSitePayload(String countryCode, Location location, TimeZone timeZone, Language primaryLanguage, Language... additionalLanguages) {
        SitePayload sitePayload = new SitePayload();
        sitePayload.setCountryCode(countryCode);
        sitePayload.setLocation(location);
        sitePayload.setTimeZone(timeZone);
        LanguageMatrix languageMatrix = new LanguageMatrix();
        languageMatrix.setPrimaryLanguage(primaryLanguage);
        languageMatrix.enable(additionalLanguages);
        sitePayload.setLanguages(languageMatrix);
        payload.setSitePayload(sitePayload);
    }

    public void buildAudioPayload() {
        AudioPayload audioPayload = new AudioPayload();
        audioPayload.setIsAudioBroadCastEnabled(false);
        audioPayload.setIsCallBackEnabled(false);
        audioPayload.setIsCallBackInternationalEnabled(false);
        audioPayload.setIsCallInGlobalEnabled(false);
        audioPayload.setIsCallInTollEnabled(false);
        audioPayload.setIsCallInTollFreeEnabled(false);
        audioPayload.setIsCloudConnectedAudioEnabled(false);
        audioPayload.setIsSipInOut(false);
        audioPayload.setIsVoipEnabled(false);
        payload.setAudioPayload(audioPayload);
    }

    public void buildStoragePayload(int capacity, boolean overage) {
        StoragePayload storagePayload = new StoragePayload();
        storagePayload.setStorageCapacity(capacity);
        storagePayload.setStorageOverage(overage);
        payload.setStoragePayload(storagePayload);
    }

    public void buildConferencePayload(ConferenceService service, int attendeeCapacity, boolean attendeeOverage,
                                       LicenseModel licenseModel, int licenseVolume, boolean licenseOverage) {
        ConferencePayload conferencePayload = new ConferencePayload();
        conferencePayload.setAttendeeCapacity(attendeeCapacity);
        conferencePayload.setAttendeeOverage(attendeeOverage);
        conferencePayload.setLicenseModel(licenseModel);
        conferencePayload.setLicenseOverage(licenseOverage);
        conferencePayload.setLicenseVolume(licenseVolume);
        switch (service) {
            case MC:
                payload.setMcPayload(conferencePayload);
                break;
            case EC:
                payload.setEcPayload(conferencePayload);
                break;
            case TC:
                payload.setTcPayload(conferencePayload);
                break;
            case SC:
                payload.setScPayload(conferencePayload);
                break;
            case EE:
                payload.setEePayload(conferencePayload);
                break;
        }

    }


    public WbxPayload build() {
        return payload;
    }
}
