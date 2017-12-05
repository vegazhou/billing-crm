package com.kt.spalgorithm.alg;

import com.kt.biz.types.ChargeType;
import com.kt.biz.types.WebExSiteState;
import com.kt.spalgorithm.alg.order.*;
import com.kt.spalgorithm.builder.RequestBuilder;
import com.kt.spalgorithm.exception.AllConferenceServicesDisabledException;
import com.kt.spalgorithm.exception.ValidationException;
import com.kt.spalgorithm.model.OrderChange;
import com.kt.spalgorithm.model.payload.ConferencePayload;
import com.kt.spalgorithm.model.payload.WbxPayload;
import com.kt.spalgorithm.model.request.Request;
import com.kt.spalgorithm.types.Action;
import com.kt.spalgorithm.types.ChangeType;
import com.kt.spalgorithm.types.LicenseModel;
import com.kt.spalgorithm.validator.PayloadValidator;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public class SiteStateTransitionAlgorithm {
    public WbxPayload calc(WbxPayload initialState, List<OrderChange> changes) {
        WbxPayload targetState = initialState.deepClone();
        for (OrderChange change : changes) {
            apply(change, targetState);
        }
        return targetState;
    }


    public List<Request> calcPayloadTransit(WbxPayload origin, WbxPayload destination) throws ValidationException {
        assert destination != null;
        new PayloadValidator(destination).validate();


        if (origin == null || origin.getState() == WebExSiteState.PROVISIONING) {
            assertNotAllServiceDisabled(destination);
            return Collections.singletonList(copyDestinationPayloadAsNew(destination));
        } else {
            if (origin.getState() == WebExSiteState.IN_EFFECT) {
                if (isAllServicesDisabled(destination)) {
                    return Collections.singletonList(copyDestinationPayloadAsSuspend(destination));
                } else {
                    return Collections.singletonList(copyDeltaPayloadAsModify(origin, destination));
                }
            } else if (origin.getState() == WebExSiteState.SUSPENDED) {
                if (isAllServicesDisabled(destination)) {
                    //original state is suspended, target state is also suspended, do nothing
                    return new LinkedList<>();
                } else {
                    Request resume = generateResumeRequest(origin);
                    Request modify = copyDeltaPayloadAsResumeModify(origin, destination);
                    return Arrays.asList(resume, modify);
                }
            }
        }
        return new LinkedList<>();
    }

    private Request generateResumeRequest(WbxPayload payload) {
        RequestBuilder builder = new RequestBuilder(Action.RESUME);
        builder.buildSiteSegment(Action.RESUME, payload.getSitePayload());
        return builder.build();
    }

    private boolean isAllServicesDisabled(WbxPayload payload) {
        if (payload.getEcPayload() == null &&
                payload.getMcPayload() == null &&
                payload.getTcPayload() == null &&
                payload.getScPayload() == null &&
                payload.getEePayload() == null) {
            return true;
        } else {
            if (isHostLicenseZero(payload.getMcPayload()) &&
                    isHostLicenseZero(payload.getEcPayload()) &&
                    isHostLicenseZero(payload.getTcPayload()) &&
                    isHostLicenseZero(payload.getScPayload()) &&
                    isHostLicenseZero(payload.getEePayload())) {
                return true;
            }
        }
        return false;
    }

    private boolean isHostLicenseZero(ConferencePayload payload) {
        if (payload == null) {
            return true;
        } else {
            if (payload.getLicenseModel() == LicenseModel.HOSTS) {
                if (payload.getLicenseVolume() <= 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private Request copyDeltaPayloadAsModify(WbxPayload origin, WbxPayload destination) {
        RequestBuilder builder = new RequestBuilder(Action.MODIFY);
        builder.buildSiteSegment(Action.MODIFY, destination.getSitePayload());
        builder.buildAudioSegment(Action.MODIFY, destination.getAudioPayload());
        builder.buildStorageSegment(Action.MODIFY, destination.getStoragePayload());
        modifyConferencePayload(builder, origin.getMcPayload(), destination.getMcPayload());
        modifyConferencePayload(builder, origin.getEcPayload(), destination.getEcPayload());
        modifyConferencePayload(builder, origin.getTcPayload(), destination.getTcPayload());
        modifyConferencePayload(builder, origin.getScPayload(), destination.getScPayload());
        modifyConferencePayload(builder, origin.getEePayload(), destination.getEePayload());
        return builder.build();
    }

    private Request copyDeltaPayloadAsResumeModify(WbxPayload origin, WbxPayload destination) {
        RequestBuilder builder = new RequestBuilder(Action.MODIFY);
        builder.buildSiteSegment(Action.MODIFY, destination.getSitePayload());
        builder.buildAudioSegment(Action.MODIFY, destination.getAudioPayload());
        builder.buildStorageSegment(Action.MODIFY, destination.getStoragePayload());
        modifyResumeConferencePayload(builder, origin.getMcPayload(), destination.getMcPayload());
        modifyResumeConferencePayload(builder, origin.getEcPayload(), destination.getEcPayload());
        modifyResumeConferencePayload(builder, origin.getTcPayload(), destination.getTcPayload());
        modifyResumeConferencePayload(builder, origin.getScPayload(), destination.getScPayload());
        modifyResumeConferencePayload(builder, origin.getEePayload(), destination.getEePayload());
        return builder.build();
    }


    private void modifyConferencePayload(RequestBuilder builder, ConferencePayload origin, ConferencePayload destination) {
        if (isConferenceServiceDisabled(origin) && isConferenceServiceDisabled(destination)) {
            //do nothing
        } else if (isConferenceServiceDisabled(origin)) {
            builder.buildConferenceSegment(Action.NEW, destination);
        } else if (isConferenceServiceDisabled(destination)) {
            builder.buildConferenceSegment(Action.CANCEL, origin);
        } else {
            if (origin.getLicenseModel() != destination.getLicenseModel()) {
                builder.buildConferenceSegment(Action.CANCEL, origin);
                builder.buildConferenceSegment(Action.NEW, destination);
            } else {
                builder.buildConferenceSegment(Action.MODIFY, destination);
            }
        }
    }

    private void modifyResumeConferencePayload(RequestBuilder builder, ConferencePayload origin, ConferencePayload destination) {
        if(isConferenceServiceEnabled(origin)){
            if(isConferenceServiceEnabled(destination)) {
                if (origin.getLicenseModel() != destination.getLicenseModel()) {
                    builder.buildConferenceSegment(Action.CANCEL, origin);
                    builder.buildConferenceSegment(Action.NEW, destination);
                } else {
                    builder.buildConferenceSegment(Action.MODIFY, destination);
                }
            }else{
                builder.buildConferenceSegment(Action.CANCEL, origin);
            }
        }else{
            builder.buildConferenceSegment(Action.NEW, destination);
        }
    }

    private Request copyDestinationPayloadAsSuspend(WbxPayload destination) {
        RequestBuilder builder = new RequestBuilder(Action.SUSPEND);
        builder.buildSiteSegment(Action.SUSPEND, destination.getSitePayload());
        return builder.build();
    }


    private Request copyDestinationPayloadAsNew(WbxPayload destination) {
        RequestBuilder builder = new RequestBuilder(Action.NEW);
        builder.buildSiteSegment(Action.NEW, destination.getSitePayload());
        builder.buildAudioSegment(Action.NEW, destination.getAudioPayload());
        builder.buildStorageSegment(Action.NEW, destination.getStoragePayload());
        builder.buildConferenceSegment(Action.NEW, destination.getMcPayload());
        builder.buildConferenceSegment(Action.NEW, destination.getEcPayload());
        builder.buildConferenceSegment(Action.NEW, destination.getTcPayload());
        builder.buildConferenceSegment(Action.NEW, destination.getScPayload());
        builder.buildConferenceSegment(Action.NEW, destination.getEePayload());
        return builder.build();
    }

    private boolean isConferenceServiceDisabled(ConferencePayload payload) {
        return !isConferenceServiceEnabled(payload);
    }

    private boolean isConferenceServiceEnabled(ConferencePayload payload) {
        return payload != null && (payload.getLicenseModel() != LicenseModel.HOSTS || payload.getLicenseVolume() > 0);
    }

    private void assertNotAllServiceDisabled(WbxPayload payload) throws ValidationException {
        if (isConferenceServiceDisabled(payload.getMcPayload()) &&
                isConferenceServiceDisabled(payload.getEcPayload()) &&
                isConferenceServiceDisabled(payload.getTcPayload()) &&
                isConferenceServiceDisabled(payload.getScPayload()) &&
                isConferenceServiceDisabled(payload.getEePayload())) {
            throw new AllConferenceServicesDisabledException();
        }
    }


    private void apply(OrderChange orderChange, WbxPayload initialState) {
        ChargeType type = orderChange.getOrder().getChargeScheme().getType();
        Alg alg = null;
        switch (type) {
            case EC_PREPAID:
                alg = new AlgEcPrepaid();
                break;
            case EC_PAY_PER_USE:
                alg = new AlgEcPPU();
                break;
            case MONTHLY_PAY_BY_HOSTS:
                alg = new AlgConferenceByHosts();
                break;
            case MONTHLY_PAY_BY_PORTS:
                alg = new AlgConferenceByPorts();
                break;
            case MONTHLY_PAY_BY_TOTAL_ATTENDEES:
                alg = new AlgConferenceByTotalAttendees();
                break;
            case MONTHLY_PAY_BY_ACTIVEHOSTS:
                alg = new AlgConferenceByHostsAH();
                break;
            case PSTN_STANDARD_CHARGE:
                alg = new AlgPSTN();
                break;
            case MONTHLY_PAY_BY_STORAGE:
                alg = new AlgStorage();
                break;
            case MONTHLY_PAY_BY_STORAGE_O:
                alg = new AlgStorageOverflow();
                break;
        }

        if (alg != null) {
            if (orderChange.getChangeType() == ChangeType.START) {
                alg.start(orderChange.getOrder(), initialState);
            } else {
                alg.terminate(orderChange.getOrder(), initialState);
            }
        }
    }
}
