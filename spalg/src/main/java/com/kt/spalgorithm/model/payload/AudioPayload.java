package com.kt.spalgorithm.model.payload;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public class AudioPayload {

    private boolean isAudioBroadCastEnabled;
    private boolean isCallBackEnabled;
    private boolean isCallBackInternationalEnabled;
    private boolean isCallInTollEnabled;
    private boolean isCallInTollFreeEnabled;
    private boolean isCallInGlobalEnabled;
    private boolean isCloudConnectedAudioEnabled;
    private boolean isVoipEnabled;
    private boolean isSipInOut;

    public AudioPayload deepClone() {
        AudioPayload clone = new AudioPayload();
        clone.isAudioBroadCastEnabled = isAudioBroadCastEnabled;
        clone.isCallBackEnabled = isCallBackEnabled;
        clone.isCallBackInternationalEnabled = isCallBackInternationalEnabled;
        clone.isCallInTollEnabled = isCallInTollEnabled;
        clone.isCallInTollFreeEnabled = isCallInTollFreeEnabled;
        clone.isCallInGlobalEnabled = isCallInGlobalEnabled;
        clone.isCloudConnectedAudioEnabled = isCloudConnectedAudioEnabled;
        clone.isVoipEnabled = isVoipEnabled;
        clone.isSipInOut = isSipInOut;
        return clone;
    }


    public boolean isAudioBroadCastEnabled() {
        return isAudioBroadCastEnabled;
    }

    public void setIsAudioBroadCastEnabled(boolean isAudioBroadCaseEnabled) {
        this.isAudioBroadCastEnabled = isAudioBroadCaseEnabled;
    }

    public boolean isCallBackEnabled() {
        return isCallBackEnabled;
    }

    public void setIsCallBackEnabled(boolean isCallBackEnabled) {
        this.isCallBackEnabled = isCallBackEnabled;
    }

    public boolean isCallBackInternationalEnabled() {
        return isCallBackInternationalEnabled;
    }

    public void setIsCallBackInternationalEnabled(boolean isCallBackInternationalEnabled) {
        this.isCallBackInternationalEnabled = isCallBackInternationalEnabled;
    }

    public boolean isCallInTollEnabled() {
        return isCallInTollEnabled;
    }

    public void setIsCallInTollEnabled(boolean isCallInTollEnabled) {
        this.isCallInTollEnabled = isCallInTollEnabled;
    }

    public boolean isCallInTollFreeEnabled() {
        return isCallInTollFreeEnabled;
    }

    public void setIsCallInTollFreeEnabled(boolean isCallInTollFreeEnabled) {
        this.isCallInTollFreeEnabled = isCallInTollFreeEnabled;
    }

    public boolean isCallInGlobalEnabled() {
        return isCallInGlobalEnabled;
    }

    public void setIsCallInGlobalEnabled(boolean isCallInGlobalEnabled) {
        this.isCallInGlobalEnabled = isCallInGlobalEnabled;
    }

    public boolean isCloudConnectedAudioEnabled() {
        return isCloudConnectedAudioEnabled;
    }

    public void setIsCloudConnectedAudioEnabled(boolean isCloudConnectedAudioEnabled) {
        this.isCloudConnectedAudioEnabled = isCloudConnectedAudioEnabled;
    }

    public boolean isVoipEnabled() {
        return isVoipEnabled;
    }

    public void setIsVoipEnabled(boolean isVoipEnabled) {
        this.isVoipEnabled = isVoipEnabled;
    }

    public boolean isSipInOut() {
        return isSipInOut;
    }

    public void setIsSipInOut(boolean isSipInOut) {
        this.isSipInOut = isSipInOut;
    }
}
