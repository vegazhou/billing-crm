package com.kt.spalgorithm;

import com.kt.biz.types.Language;
import com.kt.biz.types.Location;
import com.kt.biz.types.TimeZone;
import com.kt.spalgorithm.builder.PayloadBuilder;
import com.kt.spalgorithm.alg.SiteStateTransitionAlgorithm;
import com.kt.spalgorithm.exception.ValidationException;
import com.kt.spalgorithm.model.request.Request;
import com.kt.spalgorithm.types.*;
import org.junit.Test;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public class TestNewSite {

    @Test
    public void test() throws ValidationException {
        SiteStateTransitionAlgorithm algorithm = new SiteStateTransitionAlgorithm();
        PayloadBuilder origin = new PayloadBuilder();
        origin.buildSitePayload("CN", Location.KETIAN_CT, TimeZone.BEIJING, Language.SIMPLIFIED_CHINESE, Language.DANISH, Language.ENGLISH);
        origin.buildAudioPayload();
        origin.buildConferencePayload(ConferenceService.MC, 8, false, LicenseModel.HOSTS, 10, false);

        PayloadBuilder destination = new PayloadBuilder();
        destination.buildSitePayload("CN", Location.KETIAN_CT, TimeZone.BEIJING, Language.SIMPLIFIED_CHINESE, Language.DANISH, Language.ENGLISH);
        destination.buildAudioPayload();
        destination.buildConferencePayload(ConferenceService.MC, 8, false, LicenseModel.HOSTS, 20, false);
//        destination.buildConferencePayload(ConferenceService.EE, 8, false, LicenseModel.HOSTS, 10, false);
        destination.buildConferencePayload(ConferenceService.TC, 8, false, LicenseModel.PORTS, 0, false);


        List<Request> request = algorithm.calcPayloadTransit(origin.build(), destination.build());
        return;
    }
}
