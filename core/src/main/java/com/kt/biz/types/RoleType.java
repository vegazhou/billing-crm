package com.kt.biz.types;


/**
 * Created by Vega Zhou on 2015/11/1.
 */
public enum RoleType {

    SUPER_ADMIN(999),
    FIN_AUDITOR(800),
    CONTRACT_AUDITOR(700),
    CHARGE_AUDITOR(600),
    PRODUCT_AUDITOR(500),
    OPERATOR(100),
    USER_ADMIN(50),
    READONLY(0);



    private int value;

    RoleType(int value) {
        this.value = value;
    }


    public static RoleType valueOf(int value) {
        for (RoleType role : values()) {
            if (role.value == value) {
                return role;
            }
        }
        return null;
    }

    public boolean isPremierTo(RoleType role) {
        return value > role.value;
    }

    public boolean isPremierOrEqual(RoleType role) {
        return value >= role.value;
    }


    public boolean isEqualTo(RoleType role) {
        return value == role.value;
    }
}
