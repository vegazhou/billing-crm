package com.kt.entity.mysql.crm;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
@Table(name = "B_CHARGE_SCHEME_ATTRIBUTES")
@IdClass(AttributePrimaryKey.class)
@Entity
public class ChargeSchemeAttribute implements Attribute {

    @Column(name = "SCHEME_ID")
    @Id()
    private String entityId;

    @Column(name = "ATTRIBUTE_NAME")
    private String name;

    @Column(name = "ATTRIBUTE_VALUE")
    private String value;


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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
