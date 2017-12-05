package com.kt.entity.mysql.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * User
 */
@Table(name = "kt_b_user")
@Entity
public class OrgUser {

    /**
     * The id.
     */
    private String pid;

    /**
     * The user name.
     */
    private String userName;

    /**
     * The full name.
     */
    private String fullName;


    private String roleId;

    /**
     * Instantiates a new org user.
     */
    public OrgUser() {

    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "PID")
    public String getPid() {
        return pid;
    }

    /**
     * Sets the id.
     *
     * @param pid the new id
     */
    public void setPid(String pid) {
        this.pid = pid;
    }


    /**
     * Gets the user name.
     *
     * @return the user name
     */
    @Column(name = "USERNAME")
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name.
     *
     * @param userName the new user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the full name.
     *
     * @return the full name
     */
    @Column(name = "FULLNAME")
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the full name.
     *
     * @param fullName the new full name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    @Column(name = "ROLE_ID")
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }


}
