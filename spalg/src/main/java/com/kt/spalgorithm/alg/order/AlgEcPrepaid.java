package com.kt.spalgorithm.alg.order;

import com.kt.biz.model.order.OrderBean;
import com.kt.spalgorithm.alg.Alg;
import com.kt.spalgorithm.model.payload.ConferencePayload;
import com.kt.spalgorithm.model.payload.WbxPayload;
import com.kt.spalgorithm.types.LicenseModel;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public class AlgEcPrepaid extends Alg {

    @Override
    public void start(OrderBean order, WbxPayload initialState) {
        ConferencePayload payload = getEcPrepaidDefaultPayload();
        payload.setServiceName(order.getBiz().getType().getServiceName());
        initialState.setEcPayload(payload);
        initialState.getAudioPayload().setIsAudioBroadCastEnabled(true);
    }

    @Override
    public void terminate(OrderBean order, WbxPayload initialState) {
        initialState.setEcPayload(null);
        initialState.getAudioPayload().setIsAudioBroadCastEnabled(false);
    }


    private ConferencePayload getEcPrepaidDefaultPayload() {
        ConferencePayload payload = new ConferencePayload();
        payload.setLicenseOverage(false);
        payload.setLicenseVolume(1);
        payload.setLicenseModel(LicenseModel.HOSTS);
        payload.setAttendeeOverage(true);
        payload.setAttendeeCapacity(3000);
        return payload;
    }
}
