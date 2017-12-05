package com.kt.biz.types;

/**
 * Created by Vega Zhou on 2016/4/11.
 */
public enum AccountOperationType {
    BILL_CHARGE(1),
    DEPOSIT(2),
    REFUND(3);


    int v;

    AccountOperationType(int v) {
        this.v = v;
    }

    public int getValue() {
        return v;
    }

}
