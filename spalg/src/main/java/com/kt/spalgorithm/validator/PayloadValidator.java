package com.kt.spalgorithm.validator;

import com.kt.spalgorithm.exception.*;
import com.kt.spalgorithm.model.payload.AudioPayload;
import com.kt.spalgorithm.model.payload.ConferencePayload;
import com.kt.spalgorithm.model.payload.SitePayload;
import com.kt.spalgorithm.model.payload.WbxPayload;
import com.kt.spalgorithm.types.LicenseModel;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public class PayloadValidator {

    private WbxPayload payload;

    public PayloadValidator(WbxPayload payload) {
        assert payload != null;
        this.payload = payload;
    }

    public void validate() throws ValidationException {
        assertSitePayloadCorrect();
        assertAudioPayloadCorrect();

        //comment this, if all services are disabled, we should suspend the site
//        assertNotAllServiceDisabled();
        assertConferencePayloadsCorrect();
        assertEeXcNotConflicted();
    }

    private void assertSitePayloadCorrect() throws ValidationException {
        SitePayload sitePayload = payload.getSitePayload();
        if (sitePayload == null) {
            throw new SitePayloadMissingException();
        }

        if (sitePayload.getLocation() == null ||
                sitePayload.getLanguages().getPrimaryLanguage() == null ||
                StringUtils.isBlank(sitePayload.getCountryCode()) ||
                sitePayload.getTimeZone() == null) {
            throw new PrimaryFieldMissingException();
        }
    }


    private void assertAudioPayloadCorrect() throws ValidationException {
        AudioPayload audioPayload = payload.getAudioPayload();
        if (audioPayload == null) {
            throw new AudioPayloadMissingException();
        }
    }

    private void assertNotAllServiceDisabled() throws ValidationException {
        if (isConferenceServiceEnabled(payload.getMcPayload()) ||
                isConferenceServiceEnabled(payload.getEcPayload()) ||
                isConferenceServiceEnabled(payload.getTcPayload()) ||
                isConferenceServiceEnabled(payload.getScPayload()) ||
                isConferenceServiceEnabled(payload.getEePayload())) {
        } else {
            throw new AllConferenceServicesDisabledException();
        }
    }

    private void assertEeXcNotConflicted() throws ValidationException {
        if (isEeXcConflicted(payload)) {
            throw new CollisionDetectedBetweenConferencePayloads();
        }
    }

    private boolean isEeXcConflicted(WbxPayload payload) {
        return isConferenceServiceEnabled(payload.getEePayload()) &&
                (isConferenceServiceEnabled(payload.getMcPayload()) || isConferenceServiceEnabled(payload.getEcPayload()) || isConferenceServiceEnabled(payload.getTcPayload()) || isConferenceServiceEnabled(payload.getScPayload()));
    }

    private void assertConferencePayloadsCorrect() throws ValidationException {
        ConferencePayload mcPayload = payload.getMcPayload();
        ConferencePayload ecPayload = payload.getEcPayload();
        ConferencePayload tcPayload = payload.getTcPayload();
        ConferencePayload scPayload = payload.getScPayload();
        ConferencePayload eecPayload = payload.getEePayload();

        if (mcPayload != null) {
            assertConferencePayloadCorrect(mcPayload);
        }
        if (ecPayload != null) {
            assertConferencePayloadCorrect(ecPayload);
        }
        if (tcPayload != null) {
            assertConferencePayloadCorrect(tcPayload);
        }
        if (scPayload != null) {
            assertConferencePayloadCorrect(scPayload);
        }
        if (eecPayload != null) {
            assertConferencePayloadCorrect(eecPayload);
        }
    }

    private void assertConferencePayloadCorrect(ConferencePayload payload) throws ValidationException {
        LicenseModel licenseModel = payload.getLicenseModel();
        if (licenseModel == null) {
            throw new PrimaryFieldMissingException();
        } else if (licenseModel == LicenseModel.HOSTS) {
            assertHostLicenseModelCorrect(payload);
        } else if (licenseModel == LicenseModel.PORTS) {
            assertPortsLicenseModelCorrect(payload);
        }
    }



    private void assertHostLicenseModelCorrect(ConferencePayload conferencePayload) throws ValidationException {
        if (conferencePayload.getLicenseVolume() < 0) {
            throw new InvalidLicenseVolumeException();
        }
        if (conferencePayload.getAttendeeCapacity() <= 0) {
            throw new InvalidAttendeeCapacityException();
        }
    }

    private void assertPortsLicenseModelCorrect(ConferencePayload conferencePayload) throws InvalidAttendeeCapacityException {
        if (conferencePayload.getAttendeeCapacity() <= 0) {
            throw new InvalidAttendeeCapacityException();
        }
    }


    private boolean isConferenceServiceEnabled(ConferencePayload payload) {
        return payload != null && (payload.getLicenseModel() != LicenseModel.HOSTS || payload.getLicenseVolume() > 0);
    }
}
