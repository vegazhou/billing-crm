package com.kt.biz.types;

/**
 * Created by Vega Zhou on 2016/4/11.
 */
public enum FeeType {
    WEBEX_FIRST_INSTALLMENT(1),
    WEBEX_CONFERENCE_FEE(10),
    WEBEX_PSTN_FEE(11),
    WEBEX_STORAGE_FEE(12),
    WEBEX_OVERFLOW_FEE(13),
    WEBEX_EC_DEPOSIT(14),
    WEBEX_CMR_FEE(15),
    WEBEX_POSTPAID_CONFERENCE_FEE(16),

    CC_FIRST_INSTALLMENT(21),
    CC_FEE(22),
    CC_PSTN_FEE(23);

    int v;

    FeeType(int v) {
        this.v = v;
    }

    public int getValue() {
        return v;
    }

    public static FeeType valueOf(int v) {
        for (FeeType feeType : FeeType.values()) {
            if (feeType.v == v) {
                return feeType;
            }
        }
        return null;
    }

    public static boolean isWeBexFeeType(FeeType feeType) {
        return feeType == WEBEX_FIRST_INSTALLMENT ||
                feeType == WEBEX_CONFERENCE_FEE ||
                feeType == WEBEX_PSTN_FEE ||
                feeType == WEBEX_STORAGE_FEE ||
                feeType == WEBEX_OVERFLOW_FEE ||
                feeType == WEBEX_EC_DEPOSIT ||
                feeType == WEBEX_CMR_FEE ||
                feeType == WEBEX_POSTPAID_CONFERENCE_FEE;
    }


    public static boolean isCcFeeType(FeeType feeType) {
        return feeType == CC_FIRST_INSTALLMENT || feeType == CC_FEE || feeType == CC_PSTN_FEE;
    }
}
