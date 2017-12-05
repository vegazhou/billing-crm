package com.kt.entity.mysql.crm;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
@Table(name = "B_CHARGE_SCHEME")
@Entity
public class ChargeScheme implements SchemeEntity {

    private String id;

    private String displayName;

    private String type;

    @Column(name = "IS_TEMPLATE")
    private int isTemplate;

    @Column(name = "STATE")
    private String state;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_TIME")
    private String createdTime;

    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "DISPLAY_NAME")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIsTemplate() {
        return isTemplate;
    }

    public void setIsTemplate(int isTemplate) {
        this.isTemplate = isTemplate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String getCreatedTime() {
        return createdTime;
    }

    @Override
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
