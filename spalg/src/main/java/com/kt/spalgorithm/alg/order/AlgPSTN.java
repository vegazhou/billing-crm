package com.kt.spalgorithm.alg.order;

import com.kt.biz.model.order.OrderBean;
import com.kt.spalgorithm.alg.Alg;
import com.kt.spalgorithm.model.payload.AudioPayload;
import com.kt.spalgorithm.model.payload.WbxPayload;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public class AlgPSTN extends Alg {
    @Override
    public void start(OrderBean order, WbxPayload initialState) {
        AudioPayload audioPayload = initialState.getAudioPayload();
        audioPayload.setIsCallInTollFreeEnabled(true);
        audioPayload.setIsCallInTollEnabled(true);
        audioPayload.setIsCallInGlobalEnabled(true);
        audioPayload.setIsCallBackEnabled(true);
        audioPayload.setIsCallBackInternationalEnabled(false);
//        audioPayload.setIsCloudConnectedAudioEnabled(true);
        initialState.setAudioPayload(audioPayload);
    }

    @Override
    public void terminate(OrderBean order, WbxPayload initialState) {
        AudioPayload audioPayload = initialState.getAudioPayload();
        audioPayload.setIsCallInTollFreeEnabled(false);
        audioPayload.setIsCallInTollEnabled(false);
        audioPayload.setIsCallInGlobalEnabled(false);
        audioPayload.setIsCallBackEnabled(false);
        audioPayload.setIsCallBackInternationalEnabled(false);
        initialState.setAudioPayload(audioPayload);
    }
}
