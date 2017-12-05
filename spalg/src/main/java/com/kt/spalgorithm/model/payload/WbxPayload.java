package com.kt.spalgorithm.model.payload;


import com.kt.biz.types.WebExSiteState;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public class WbxPayload {
    WebExSiteState state;
    SitePayload sitePayload;
    AudioPayload audioPayload;
    StoragePayload storagePayload;

    ConferencePayload mcPayload;
    ConferencePayload ecPayload;
    ConferencePayload tcPayload;
    ConferencePayload scPayload;
    ConferencePayload eePayload;

    public SitePayload getSitePayload() {
        return sitePayload;
    }

    public void setSitePayload(SitePayload sitePayload) {
        this.sitePayload = sitePayload;
    }

    public AudioPayload getAudioPayload() {
        return audioPayload;
    }

    public void setAudioPayload(AudioPayload audioPayload) {
        this.audioPayload = audioPayload;
    }

    public StoragePayload getStoragePayload() {
        return storagePayload;
    }

    public void setStoragePayload(StoragePayload storagePayload) {
        this.storagePayload = storagePayload;
    }

    public ConferencePayload getMcPayload() {
        return mcPayload;
    }

    public void setMcPayload(ConferencePayload mcPayload) {
        this.mcPayload = mcPayload;
    }

    public ConferencePayload getEcPayload() {
        return ecPayload;
    }

    public void setEcPayload(ConferencePayload ecPayload) {
        this.ecPayload = ecPayload;
    }

    public ConferencePayload getTcPayload() {
        return tcPayload;
    }

    public void setTcPayload(ConferencePayload tcPayload) {
        this.tcPayload = tcPayload;
    }

    public ConferencePayload getScPayload() {
        return scPayload;
    }

    public void setScPayload(ConferencePayload scPayload) {
        this.scPayload = scPayload;
    }

    public ConferencePayload getEePayload() {
        return eePayload;
    }

    public void setEePayload(ConferencePayload eePayload) {
        this.eePayload = eePayload;
    }

    public WebExSiteState getState() {
        return state;
    }

    public void setState(WebExSiteState state) {
        this.state = state;
    }

    public WbxPayload deepClone() {
        WbxPayload clone = new WbxPayload();
        if (sitePayload != null) {
            clone.setSitePayload(sitePayload.deepClone());
        }
        if (audioPayload != null) {
            clone.setAudioPayload(audioPayload.deepClone());
        }
        if (storagePayload != null) {
            clone.setStoragePayload(storagePayload.deepClone());
        }
        if (mcPayload != null) {
            clone.setMcPayload(mcPayload.deepClone());
        }
        if (ecPayload != null) {
            clone.setEcPayload(ecPayload.deepClone());
        }
        if (tcPayload != null) {
            clone.setTcPayload(tcPayload.deepClone());
        }
        if (scPayload != null) {
            clone.setScPayload(scPayload.deepClone());
        }
        if (eePayload != null) {
            clone.setEePayload(eePayload.deepClone());
        }
        return clone;
    }
}
