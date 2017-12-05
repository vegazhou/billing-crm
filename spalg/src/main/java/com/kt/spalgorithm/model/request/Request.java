package com.kt.spalgorithm.model.request;

import com.kt.spalgorithm.types.Action;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public class Request {
    private Action action;

    private SiteSegment siteSegment;

    private List<ConferenceSegment> conferenceSegments = new LinkedList<>();

    private AudioSegment audioSegment;

    private StorageSegment storageSegment;

    public Request(Action action) {
        this.action = action;
    }

    public SiteSegment getSiteSegment() {
        return siteSegment;
    }

    public void setSiteSegment(SiteSegment siteSegment) {
        this.siteSegment = siteSegment;
    }

    public List<ConferenceSegment> getConferenceSegments() {
        return conferenceSegments;
    }

    public void setConferenceSegments(List<ConferenceSegment> conferenceSegments) {
        this.conferenceSegments = conferenceSegments;
    }

    public void addConferenceSegment(ConferenceSegment conferenceSegment) {
        conferenceSegments.add(conferenceSegment);
    }

    public AudioSegment getAudioSegment() {
        return audioSegment;
    }

    public void setAudioSegment(AudioSegment audioSegment) {
        this.audioSegment = audioSegment;
    }

    public StorageSegment getStorageSegment() {
        return storageSegment;
    }

    public void setStorageSegment(StorageSegment storageSegment) {
        this.storageSegment = storageSegment;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
