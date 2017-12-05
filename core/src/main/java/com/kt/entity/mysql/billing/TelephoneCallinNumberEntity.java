package com.kt.entity.mysql.billing;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/7.
 */
@Table(name = "TELEPHONE_CALLIN_NUMBER_T" )
@Entity
public class TelephoneCallinNumberEntity {

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessNumber() {
        return accessNumber;
    }

    public void setAccessNumber(String accessNumber) {
        this.accessNumber = accessNumber;
    }

    public String getLandKind() {
        return landKind;
    }

    public void setLandKind(String landKind) {
        this.landKind = landKind;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }

    public int getTollType() {
        return tollType;
    }

    public void setTollType(int tollType) {
        this.tollType = tollType;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Column(name = "ACCESS_NUMBER")
    private String accessNumber;
    @Column(name = "LAND_KIND")
    private String landKind;
    @Column(name = "NOTE")
    private String note;
    @Column(name = "CALL_TYPE")
    private int callType;
    @Column(name = "TOLL_TYPE")
    private int tollType;
    @Column(name = "BEGIN_DATE")
    private String beginDate;
    @Column(name = "END_DATE")
    private Date endDate;
    @Column(name = "VALID")
    private boolean valid;
    @Column(name = "CODE")
    private int code;
}
