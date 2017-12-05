package com.kt.spalgorithm.model.request;

import com.kt.spalgorithm.model.payload.SitePayload;
import com.kt.spalgorithm.types.Action;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public class SiteSegment {
    private Action action;
    private SitePayload payload;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        assert action != Action.CANCEL;
        this.action = action;
    }

    public SitePayload getPayload() {
        return payload;
    }

    public void setPayload(SitePayload payload) {
        this.payload = payload;
    }
}

