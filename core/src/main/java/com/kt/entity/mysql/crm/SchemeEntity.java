package com.kt.entity.mysql.crm;


/**
 * Created by Vega Zhou on 2016/3/11.
 */
public interface SchemeEntity {
    void setId(String id);
    String getId();

    void setDisplayName(String displayName);
    String getDisplayName();

    String getType();
    void setType(String type);

    String getCreatedBy();
    void setCreatedBy(String owner);

    String getCreatedTime();
    void setCreatedTime(String createdTime);
}
