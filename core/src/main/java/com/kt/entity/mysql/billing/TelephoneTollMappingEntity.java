package com.kt.entity.mysql.billing;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/7.
 */
@Table(name = "TELEPHONE_400_EXCHANGE_T" )
@Entity
public class TelephoneTollMappingEntity {

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID")
    private String id;

    @Column(name = "REAL_NUMBER")
    private String realNumber;

    public String getRealNumber() {
        return realNumber;
    }

    public void setRealNumber(String realNumber) {
        this.realNumber = realNumber;
    }

    public String getAccessNumber() {
        return accessNumber;
    }

    public void setAccessNumber(String accessNumber) {
        this.accessNumber = accessNumber;
    }

    public String getTeleKind() {
        return teleKind;
    }

    public void setTeleKind(String teleKind) {
        this.teleKind = teleKind;
    }

    public boolean getValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Column(name = "ACCESS_NUMBER")
    private String accessNumber;

    @Column(name = "TELE_KIND")
    private String teleKind;

    @Column(name = "VALID")
    private boolean valid;

    @Column(name = "BEGIN_DATE")
    private String beginDate;

    @Column(name = "END_DATE")
    private Date endDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Transient
    private String code;

}
