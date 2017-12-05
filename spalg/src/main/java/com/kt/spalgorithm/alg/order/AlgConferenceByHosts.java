package com.kt.spalgorithm.alg.order;

import com.kt.biz.model.biz.AbstractBizScheme;
import com.kt.biz.model.biz.impl.BizWebExConf;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayByHosts;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.types.BizType;
import com.kt.spalgorithm.alg.Alg;
import com.kt.spalgorithm.model.payload.ConferencePayload;
import com.kt.spalgorithm.model.payload.WbxPayload;
import com.kt.spalgorithm.types.LicenseModel;
import org.apache.log4j.Logger;

/**
 * Created by Vega Zhou on 2016/3/28.
 */
public class AlgConferenceByHosts extends Alg {

    private static final Logger LOGGER = Logger.getLogger(AlgConferenceByHosts.class);

    private static final int MAX_LICENSE_NUMBER = 999999;

    @Override
    public void start(OrderBean order, WbxPayload initialState) {
        WebExConfMonthlyPayByHosts chargeScheme = getChargeScheme(order);
        if (chargeScheme == null) {
            LOGGER.error("charge scheme is not WebExConfMonthlyPayByHosts");
            return;
        }
        BizWebExConf bizScheme = getBiz(order);
        if (bizScheme == null) {
            LOGGER.error("biz scheme is not BizWebExConf");
            return;
        }

        ConferencePayload payload = getPayload(order, initialState);
        if (payload == null) {
            payload = initializePayload();
        }else if(LicenseModel.PORTS.equals(payload.getLicenseModel())){
            payload.setLicenseOverage(false);
            payload.setAttendeeOverage(false);
            payload.setLicenseModel(LicenseModel.HOSTS);
            payload.setLicenseVolume(0);
        }else if (payload.getLicenseVolume()==MAX_LICENSE_NUMBER){//Active host offer
            payload.setLicenseVolume(0);
        }
        payload.setServiceName(order.getBiz().getType().getServiceName());
        payload.setAttendeeCapacity(bizScheme.getPorts());
        payload.setLicenseVolume(payload.getLicenseVolume() + chargeScheme.getHosts());
        updatePayload(order, initialState, payload);
    }

    @Override
    public void terminate(OrderBean order, WbxPayload initialState) {
        WebExConfMonthlyPayByHosts chargeScheme = getChargeScheme(order);
        if (chargeScheme == null) {
            LOGGER.error("charge scheme is not WebExConfMonthlyPayByHosts");
            return;
        }
        BizWebExConf bizScheme = getBiz(order);
        if (bizScheme == null) {
            LOGGER.error("biz scheme is not BizWebExConf");
            return;
        }

        ConferencePayload payload = getPayload(order, initialState);
        if (payload == null) {
            LOGGER.error("original payload not found while terminating");
            return;
        }

        int originalLicenseVolume = payload.getLicenseVolume();
        payload.setLicenseVolume(originalLicenseVolume - chargeScheme.getHosts());
        updatePayload(order, initialState, payload);
    }


    private ConferencePayload getPayload(OrderBean order, WbxPayload initialState) {
        BizType bizType = order.getBiz().getType();
        switch (bizType) {
            case WEBEX_MC:
                return initialState.getMcPayload();
            case WEBEX_EC:
                return initialState.getEcPayload();
            case WEBEX_TC:
                return initialState.getTcPayload();
            case WEBEX_SC:
                return initialState.getScPayload();
            case WEBEX_EE:
                return initialState.getEePayload();
        }
        return null;
    }

    private BizWebExConf getBiz(OrderBean order) {
        AbstractBizScheme bizScheme = order.getBiz();
        if (bizScheme instanceof BizWebExConf) {
            return (BizWebExConf) bizScheme;
        }
        return null;
    }

    private WebExConfMonthlyPayByHosts getChargeScheme(OrderBean order) {
        AbstractChargeScheme chargeScheme = order.getChargeScheme();
        if (chargeScheme instanceof WebExConfMonthlyPayByHosts) {
            return (WebExConfMonthlyPayByHosts) chargeScheme;
        }
        return null;
    }

    private ConferencePayload initializePayload() {
        ConferencePayload payload = new ConferencePayload();
        payload.setLicenseOverage(false);
        payload.setAttendeeOverage(false);
        payload.setLicenseModel(LicenseModel.HOSTS);
        return payload;
    }

    private void updatePayload(OrderBean order, WbxPayload targetState, ConferencePayload payload) {
        BizType bizType = order.getBiz().getType();
        switch (bizType) {
            case WEBEX_MC:
                targetState.setMcPayload(payload);
                break;
            case WEBEX_EC:
                targetState.setEcPayload(payload);
                break;
            case WEBEX_TC:
                targetState.setTcPayload(payload);
                break;
            case WEBEX_SC:
                targetState.setScPayload(payload);
                break;
            case WEBEX_EE:
                targetState.setEePayload(payload);
                break;
        }
    }
}
