package com.kt.session;

import com.kt.biz.user.RoleTranslator;
import com.kt.biz.types.RoleType;

import java.util.List;

/**
 * Created by Vega Zhou on 2015/10/31.
 */
public class Principal {

    private List<RoleType> roles;

    private String userName;




    public List<RoleType> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleType> roles) {
        this.roles = roles;
    }

    public void setRoles(String roleIds) {
        this.roles = RoleTranslator.translate(roleIds);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
