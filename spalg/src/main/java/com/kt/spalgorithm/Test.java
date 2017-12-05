package com.kt.spalgorithm;

import com.kt.biz.model.biz.impl.BizWebExEC;
import com.kt.biz.model.biz.impl.BizWebExMC;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayByHosts;
import com.kt.biz.model.charge.impl.WebExECPayPerUse;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.types.Language;
import com.kt.biz.types.Location;
import com.kt.biz.types.TimeZone;
import com.kt.biz.types.WebExSiteState;
import com.kt.spalgorithm.alg.SiteStateTransitionAlgorithm;
import com.kt.spalgorithm.exception.ValidationException;
import com.kt.biz.site.LanguageMatrix;
import com.kt.spalgorithm.model.OrderChange;
import com.kt.spalgorithm.model.payload.AudioPayload;
import com.kt.spalgorithm.model.payload.ConferencePayload;
import com.kt.spalgorithm.model.payload.SitePayload;
import com.kt.spalgorithm.model.payload.WbxPayload;
import com.kt.spalgorithm.model.request.Request;
import com.kt.spalgorithm.types.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public class Test {


    public static void main(String args[]) throws ValidationException {
        SiteStateTransitionAlgorithm algorithm = new SiteStateTransitionAlgorithm();
        List<OrderChange> changes = new ArrayList<>();
//        changes.add(new OrderChange(ChangeType.TERMINATE, getEcByHostsOrder()));
        changes.add(new OrderChange(ChangeType.START, getECPPUOrder()));
        WbxPayload origin = getInitState();
        WbxPayload target = algorithm.calc(origin, changes);
        List<Request> r = algorithm.calcPayloadTransit(origin, target);
        return;
    }


    private static WbxPayload getInitState() {
        WbxPayload payload = new WbxPayload();
        payload.setState(WebExSiteState.IN_EFFECT);
        payload.setSitePayload(getSitePayload());
        payload.setAudioPayload(getAudioPayload());
//        payload.setMcPayload(getMC25ByHostsPayload());
//        payload.setEcPayload(getECByPortsPayload());
        return payload;
    }

    private static SitePayload getSitePayload() {
        SitePayload sitePayload = new SitePayload();
        sitePayload.setLocation(Location.KETIAN_CT);
        sitePayload.setCountryCode("CN");
        sitePayload.setTimeZone(TimeZone.BEIJING);
        LanguageMatrix languages = new LanguageMatrix();
        languages.setPrimaryLanguage(Language.SIMPLIFIED_CHINESE);
        languages.enable(Language.ENGLISH);
        sitePayload.setLanguages(languages);
        return sitePayload;
    }

    private static AudioPayload getAudioPayload() {
        AudioPayload audioPayload = new AudioPayload();
        audioPayload.setIsAudioBroadCastEnabled(false);
        audioPayload.setIsCloudConnectedAudioEnabled(false);

        audioPayload.setIsVoipEnabled(true);

        audioPayload.setIsCallBackEnabled(false);
        audioPayload.setIsCallBackInternationalEnabled(false);


        audioPayload.setIsCallInTollEnabled(false);
        audioPayload.setIsCallInTollFreeEnabled(false);
        audioPayload.setIsCallInGlobalEnabled(false);
        return audioPayload;
    }


    private static ConferencePayload getMC25ByHostsPayload() {
        ConferencePayload payload = new ConferencePayload();
        payload.setAttendeeCapacity(25);
        payload.setLicenseVolume(10);
        payload.setLicenseModel(LicenseModel.HOSTS);
        payload.setAttendeeOverage(false);
        payload.setLicenseOverage(false);
        return payload;
    }

    private static ConferencePayload getEC100ByHostsPayload() {
        ConferencePayload payload = new ConferencePayload();
        payload.setAttendeeCapacity(100);
        payload.setLicenseVolume(10);
        payload.setLicenseModel(LicenseModel.HOSTS);
        payload.setAttendeeOverage(false);
        payload.setLicenseOverage(false);
        return payload;
    }


    private static ConferencePayload getTC50ByHostsPayload() {
        ConferencePayload payload = new ConferencePayload();
        payload.setAttendeeCapacity(50);
        payload.setLicenseVolume(10);
        payload.setLicenseModel(LicenseModel.HOSTS);
        payload.setAttendeeOverage(false);
        payload.setLicenseOverage(false);
        return payload;
    }

    private static ConferencePayload getMCByPortsPayload() {
        ConferencePayload payload = new ConferencePayload();
        payload.setAttendeeCapacity(100);
        payload.setLicenseVolume(10);
        payload.setLicenseModel(LicenseModel.PORTS);
        payload.setAttendeeOverage(true);
        payload.setLicenseOverage(true);
        return payload;
    }


    private static ConferencePayload getECByPortsPayload() {
        ConferencePayload payload = new ConferencePayload();
        payload.setAttendeeCapacity(100);
        payload.setServiceName("EC");
        payload.setLicenseVolume(10);
        payload.setLicenseModel(LicenseModel.PORTS);
        payload.setAttendeeOverage(true);
        payload.setLicenseOverage(true);
        return payload;
    }

    private static ConferencePayload getTCByPortsPayload() {
        ConferencePayload payload = new ConferencePayload();
        payload.setAttendeeCapacity(100);
        payload.setLicenseVolume(10);
        payload.setLicenseModel(LicenseModel.PORTS);
        payload.setAttendeeOverage(true);
        payload.setLicenseOverage(true);
        return payload;
    }


    private static OrderBean getMC25ByHostsOrder() {
        OrderBean order = new OrderBean(UUID.randomUUID().toString());
        BizWebExMC biz = new BizWebExMC();
        biz.setPorts(25);
        order.setBiz(biz);
        WebExConfMonthlyPayByHosts charge = new WebExConfMonthlyPayByHosts();
        charge.setHosts(3);
        order.setChargeScheme(charge);
        return order;
    }


    private static OrderBean getECPPUOrder() {
        OrderBean order = new OrderBean(UUID.randomUUID().toString());
        BizWebExEC biz = new BizWebExEC();
        biz.setPorts(3000);
        order.setBiz(biz);
        WebExECPayPerUse charge = new WebExECPayPerUse();
        charge.setTimes(3);
        order.setChargeScheme(charge);
        return order;
    }


    private static OrderBean getEcByHostsOrder() {
        OrderBean order = new OrderBean(UUID.randomUUID().toString());
        BizWebExEC biz = new BizWebExEC();
        biz.setPorts(3000);
        order.setBiz(biz);
        WebExConfMonthlyPayByHosts charge = new WebExConfMonthlyPayByHosts();
        charge.setHosts(2);
        order.setChargeScheme(charge);
        return order;
    }
}
