package com.kt.spalgorithm.builder;

import com.kt.spalgorithm.model.payload.AudioPayload;
import com.kt.spalgorithm.model.payload.ConferencePayload;
import com.kt.spalgorithm.model.payload.SitePayload;
import com.kt.spalgorithm.model.payload.StoragePayload;
import com.kt.spalgorithm.model.request.*;
import com.kt.spalgorithm.types.Action;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public class RequestBuilder {

    private Request request;

    public RequestBuilder(Action action) {
        request = new Request(action);
    }

    public RequestBuilder buildSiteSegment(Action action, SitePayload payload) {
        SiteSegment siteSegment = new SiteSegment();
        siteSegment.setAction(action);
        siteSegment.setPayload(payload);
        request.setSiteSegment(siteSegment);
        return this;
    }

    public RequestBuilder buildAudioSegment(Action action, AudioPayload payload) {
        AudioSegment audioSegment = new AudioSegment();
        audioSegment.setAction(action);
        audioSegment.setPayload(payload);
        request.setAudioSegment(audioSegment);
        return this;
    }

    public RequestBuilder buildStorageSegment(Action action, StoragePayload payload) {
        if (payload == null) {
            return this;
        }
        StorageSegment storageSegment = new StorageSegment();
        storageSegment.setAction(action);
        storageSegment.setPayload(payload);
        request.setStorageSegment(storageSegment);
        return this;
    }

    public RequestBuilder buildConferenceSegment(Action action, ConferencePayload payload) {
        if (payload == null) {
            return this;
        }
        ConferenceSegment conferenceSegment = new ConferenceSegment();
        conferenceSegment.setAction(action);
        conferenceSegment.setPayload(payload);
        request.addConferenceSegment(conferenceSegment);
        return this;
    }

    public Request build() {
        return request;
    }
}
