package com.kt.biz.model.util;

import com.kt.biz.types.BizType;
import com.kt.biz.types.ChargeType;

import java.util.*;

/**
 * Created by Vega Zhou on 2016/3/14.
 */
public class BizChargeCompatibility {

    private static Map<BizType, ChargeType[]> mapping = new HashMap<>();

    static {
        mapping.put(BizType.WEBEX_MC, new ChargeType[]{ChargeType.MONTHLY_PAY_BY_HOSTS, ChargeType.MONTHLY_PAY_BY_PORTS, ChargeType.MONTHLY_PAY_BY_TOTAL_ATTENDEES, ChargeType.TELECOM_CHARGE, ChargeType.MONTHLY_PAY_BY_ACTIVEHOSTS, ChargeType.MONTHLY_PAY_PERSONAL_WEBEX});
        mapping.put(BizType.WEBEX_EC, new ChargeType[]{ChargeType.MONTHLY_PAY_BY_HOSTS, ChargeType.MONTHLY_PAY_BY_PORTS, ChargeType.MONTHLY_PAY_BY_TOTAL_ATTENDEES, ChargeType.EC_PAY_PER_USE, ChargeType.EC_PREPAID, ChargeType.MONTHLY_PAY_BY_ACTIVEHOSTS});
        mapping.put(BizType.WEBEX_TC, new ChargeType[]{ChargeType.MONTHLY_PAY_BY_HOSTS, ChargeType.MONTHLY_PAY_BY_PORTS, ChargeType.MONTHLY_PAY_BY_TOTAL_ATTENDEES, ChargeType.MONTHLY_PAY_BY_ACTIVEHOSTS});
        mapping.put(BizType.WEBEX_SC, new ChargeType[]{ChargeType.MONTHLY_PAY_BY_HOSTS, ChargeType.MONTHLY_PAY_BY_PORTS, ChargeType.MONTHLY_PAY_BY_TOTAL_ATTENDEES, ChargeType.MONTHLY_PAY_BY_ACTIVEHOSTS});
        mapping.put(BizType.WEBEX_EE, new ChargeType[]{ChargeType.MONTHLY_PAY_BY_HOSTS, ChargeType.MONTHLY_PAY_BY_PORTS, ChargeType.MONTHLY_PAY_BY_TOTAL_ATTENDEES, ChargeType.MONTHLY_PAY_BY_ACTIVEHOSTS});
        mapping.put(BizType.WEBEX_STORAGE, new ChargeType[]{ChargeType.MONTHLY_PAY_BY_STORAGE, ChargeType.MONTHLY_PAY_BY_STORAGE_O});
        mapping.put(BizType.WEBEX_CMR, new ChargeType[]{ChargeType.CMR_MONTHLY_PAY});
        mapping.put(BizType.WEBEX_PSTN, new ChargeType[]{ChargeType.PSTN_STANDARD_CHARGE, ChargeType.PSTN_PERSONAL_CHARGE, ChargeType.PSTN_PERSONAL_PACKET,
                ChargeType.PSTN_MONTHLY_PACKET, ChargeType.PSTN_SINGLE_PACKET_FOR_MULTIPLE_SITES, ChargeType.PSTN_SINGLE_PACKET_FOR_ALL_SITES});
        mapping.put(BizType.MISC, new ChargeType[]{ChargeType.MISC_CHARGE});
        mapping.put(BizType.CC, new ChargeType[]{ChargeType.CC_CALLCENTER_MONTHLY_PAY, ChargeType.CC_CALLCENTER_NUMBER_MONTHLY_PAY, ChargeType.CC_CALLCENTER_PSTN, ChargeType.CC_CALLCENTER_PSTN_PACKAGE,
                ChargeType.CC_CALLCENTER_PSTN_MONTHLY_PACKAGE, ChargeType.CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE, ChargeType.CC_CALLCENTER_OLS_MONTHLY_PAY});
    }


    public static ChargeType[] getAvailableChargeTypes(BizType bizType) {
        if (mapping.containsKey(bizType)) {
            return mapping.get(bizType);
        } else {
            return new ChargeType[0];
        }
    }

    public static List<String> getAvailableChargeTypesAsStringList(BizType bizType) {
        if (mapping.containsKey(bizType)) {
            ChargeType[] types = mapping.get(bizType);
            List<String> results = new ArrayList<>();
            for (ChargeType type : types) {
                results.add(type.toString());
            }
            return results;
        } else {
            return Collections.emptyList();
        }
    }


    public static boolean isCompatible(BizType bType, ChargeType cType) {
        ChargeType[] allowedTypes = mapping.get(bType);
        if (allowedTypes == null || allowedTypes.length == 0) {
            return false;
        }
        for (ChargeType allowedType : allowedTypes) {
            if (allowedType == cType) {
                return true;
            }
        }
        return false;
    }
}
