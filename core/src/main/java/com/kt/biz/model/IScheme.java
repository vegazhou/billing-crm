package com.kt.biz.model;

import com.google.gson.JsonObject;
import com.kt.biz.model.order.CompletionCheckResult;
import com.kt.biz.types.SchemeType;
import com.kt.exception.SchemeValidationException;
import com.kt.exception.WafException;


/**
 * Created by Vega Zhou on 2016/3/8.
 */
public interface IScheme {

    void setId(String id);
    String getId();

    void setDisplayName(String displayName);
    String getDisplayName();

    void setCreatedBy(String owner);
    String getCreatedBy();

    void setCreatedTime(String createdTime);
    String getCreatedTime();

    SchemeType getType();

    void save();

    void validate() throws SchemeValidationException;

    void load(String id) throws WafException;

    void purge();



    void loadFromJson(JsonObject json) throws WafException;

    JsonObject saveAsJson();

    IScheme instantiate();

    CompletionCheckResult checkCompletion();
}
