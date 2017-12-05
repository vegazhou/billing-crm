package com.kt.spalgorithm.model.request;

import com.kt.spalgorithm.model.payload.ConferencePayload;
import com.kt.spalgorithm.types.Action;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public class ConferenceSegment {

    private Action action;

    private ConferencePayload payload;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        assert action != Action.SUSPEND && action != Action.RESUME;
        this.action = action;
    }

    public ConferencePayload getPayload() {
        return payload;
    }

    public void setPayload(ConferencePayload payload) {
        this.payload = payload;
    }
}
