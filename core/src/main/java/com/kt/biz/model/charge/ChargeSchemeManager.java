package com.kt.biz.model.charge;

import com.kt.biz.model.charge.impl.*;
import com.kt.biz.types.ChargeType;
import org.apache.log4j.Logger;

/**
 * Created by Vega Zhou on 2016/3/8.
 */
public class ChargeSchemeManager {
    private static final Logger LOGGER = Logger.getLogger(ChargeSchemeManager.class);

//    private static Map<ChargeType, Class<? extends IScheme>> mapping = new HashMap<>();
//
//    static {
//        mapping.put(ChargeType.MONTHLY_PAY_BY_HOSTS, WebExConfMonthlyPayByHosts.class);
//        mapping.put(ChargeType.MONTHLY_PAY_BY_PORTS, WebExConfMonthlyPayByPorts.class);
//        mapping.put(ChargeType.EC_PAY_PER_USE, WebExECPayPerUse.class);
//        mapping.put(ChargeType.EC_PREPAID, WebExECPrepaid.class);
//        mapping.put(ChargeType.MONTHLY_PAY_BY_STORAGE, WebExStorageMonthlyPay.class);
//        mapping.put(ChargeType.PSTN_STANDARD_CHARGE, PSTNStandardCharge.class);

//        mapping.put(ChargeType.PSTN_MONTHLY_PACKET, PSTNMonthlyPacketCharge.class);
//        mapping.put(ChargeType.PSTN_SINGLE_PACKET_FOR_MULTIPLE_SITES, PSTNSinglePacket4MultiSites.class);
//        mapping.put(ChargeType.PSTN_SINGLE_PACKET_FOR_ALL_SITES, PSTNSinglePacket4AllSites.class);
//    }


    public static AbstractChargeScheme newInstance(ChargeType chargeType) {
        try {
            return chargeType.getChargeSchemeClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
