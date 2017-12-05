package com.kt.spalgorithm.alg.order;

import com.kt.biz.model.biz.AbstractBizScheme;
import com.kt.biz.model.biz.impl.BizWebExConf;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayByAH;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayByHosts;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.types.BizType;
import com.kt.spalgorithm.alg.Alg;
import com.kt.spalgorithm.model.payload.ConferencePayload;
import com.kt.spalgorithm.model.payload.WbxPayload;
import com.kt.spalgorithm.types.LicenseModel;
import org.apache.log4j.Logger;

/**
 * Created by Rickey on 2016/10/11.
 */
public class AlgConferenceByHostsAH extends Alg {

    private static final Logger LOGGER = Logger.getLogger(AlgConferenceByHostsAH.class);

    private static final int MAX_LICENSE_NUMBER = 999999;

    @Override
    public void start(OrderBean order, WbxPayload initialState) {
        WebExConfMonthlyPayByAH chargeScheme = getChargeScheme(order);
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
        }
        payload.setLicenseVolume(MAX_LICENSE_NUMBER);
        payload.setServiceName(order.getBiz().getType().getServiceName());
        payload.setAttendeeCapacity(bizScheme.getPorts());
        updatePayload(order, initialState, payload);
    }

    @Override
    public void terminate(OrderBean order, WbxPayload initialState) {
        WebExConfMonthlyPayByAH chargeScheme = getChargeScheme(order);
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
        updatePayload(order, initialState, null);
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

    private WebExConfMonthlyPayByAH getChargeScheme(OrderBean order) {
        AbstractChargeScheme chargeScheme = order.getChargeScheme();
        if (chargeScheme instanceof WebExConfMonthlyPayByAH) {
            return (WebExConfMonthlyPayByAH) chargeScheme;
        }
        return null;
    }

    private ConferencePayload initializePayload() {
        ConferencePayload payload = new ConferencePayload();
        payload.setLicenseOverage(false);
        payload.setAttendeeOverage(false);
        payload.setLicenseModel(LicenseModel.HOSTS);
        payload.setLicenseVolume(MAX_LICENSE_NUMBER);
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
