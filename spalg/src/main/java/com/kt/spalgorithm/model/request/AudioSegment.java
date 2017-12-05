package com.kt.spalgorithm.model.request;

import com.kt.spalgorithm.model.payload.AudioPayload;
import com.kt.spalgorithm.types.Action;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public class AudioSegment {

    private Action action;

    private AudioPayload payload;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        assert action != Action.SUSPEND && action != Action.RESUME;
        this.action = action;
    }

    public AudioPayload getPayload() {
        return payload;
    }

    public void setPayload(AudioPayload payload) {
        this.payload = payload;
    }
}
