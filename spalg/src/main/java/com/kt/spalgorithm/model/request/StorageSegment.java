package com.kt.spalgorithm.model.request;

import com.kt.spalgorithm.model.payload.StoragePayload;
import com.kt.spalgorithm.types.Action;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public class StorageSegment {
    private Action action;
    private StoragePayload payload;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        assert action != Action.SUSPEND && action != Action.RESUME;
        this.action = action;
    }

    public StoragePayload getPayload() {
        return payload;
    }

    public void setPayload(StoragePayload payload) {
        this.payload = payload;
    }
}
