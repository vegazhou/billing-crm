package com.kt.api.bean.charge;

import com.kt.validation.annotation.InOneOf;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by Vega Zhou on 2016/3/12.
 */
public class ChargeScheme4Create {

    @Size(min = 0, max = 100, message = "charge.scheme.displayName.Size")
    @NotBlank(message = "charge.scheme.displayName.NotBlank")
    private String displayName;

    @InOneOf(candidates = {"MONTHLY_PAY_BY_HOSTS", "MONTHLY_PAY_BY_PORTS", "EC_PAY_PER_USE",
            "EC_PREPAID", "MONTHLY_PAY_BY_STORAGE", "PSTN_STANDARD_CHARGE", "PSTN_PACKAGE_CHARGE",
            "PSTN_MONTHLY_PACKET", "PSTN_SINGLE_PACKET_FOR_MULTIPLE_SITES", "PSTN_SINGLE_PACKET_FOR_ALL_SITES",
            "TELECOM_CHARGE", "MISC_CHARGE", "MONTHLY_PAY_BY_ACTIVEHOSTS","CMR_MONTHLY_PAY", "MONTHLY_PAY_BY_STORAGE_O",
            "MONTHLY_PAY_BY_TOTAL_ATTENDEES", "MONTHLY_PAY_PERSONAL_WEBEX", "PSTN_PERSONAL_CHARGE","PSTN_PERSONAL_PACKET",
            "CC_CALLCENTER_MONTHLY_PAY", "CC_CALLCENTER_NUMBER_MONTHLY_PAY", "CC_CALLCENTER_PSTN", "CC_CALLCENTER_PSTN_PACKAGE",
            "CC_CALLCENTER_PSTN_MONTHLY_PACKAGE", "CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE", "CC_CALLCENTER_OLS_MONTHLY_PAY"},
            message = "charge.scheme.chargeType.Invalid")
    private String chargeType;


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }
}
