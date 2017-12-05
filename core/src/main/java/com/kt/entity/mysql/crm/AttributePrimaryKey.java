package com.kt.entity.mysql.crm;

import java.io.Serializable;

/**
 * Created by Vega Zhou on 2016/3/9.
 */
public class AttributePrimaryKey implements Serializable {
    private String entityId;
    private String name;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AttributePrimaryKey) {
            AttributePrimaryKey o = (AttributePrimaryKey) obj;
            return name.equals(o.getName()) && entityId.equals(o.getEntityId());
        } else {
            return false;
        }
    }
}
