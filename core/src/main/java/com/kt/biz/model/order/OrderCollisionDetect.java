package com.kt.biz.model.order;

import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.*;
import com.kt.biz.model.order.compatcheck.*;
import com.kt.biz.types.ChargeType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vega Zhou on 2016/3/21.
 */
public class OrderCollisionDetect {

    private static Map<Class<? extends AbstractChargeScheme>, Class<? extends CollisionDetector>> mapping = new HashMap<>();

    static {
        for (ChargeType chargeType : ChargeType.values()) {
            mapping.put(chargeType.getChargeSchemeClass(), chargeType.getCollisionDetector());
        }
    }

    /**
     * Check if the new order is compatible with the existing orders
     *
     * @param newOrder the new order to be checked
     * @param context the context orders be checked against
     * @return true if the new order is compatible with the existing orders, otherwise return false
     */
    public static CollisionDetectResult check(OrderBean newOrder, List<OrderBean> context) {
        CollisionDetector checker = buildChecker(newOrder);
        if (checker != null) {
            return checker.detectAgainst(context);
        }
        return CollisionDetectResult.ok();
    }


    private static CollisionDetector buildChecker(OrderBean order) {
        AbstractChargeScheme chargeScheme = order.getChargeScheme();
        CollisionDetector checker = null;
        Class<? extends CollisionDetector> collisionDetectorClass = mapping.get(chargeScheme.getClass());
        if (collisionDetectorClass != null) {
            try {
                checker = collisionDetectorClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
//        if (chargeScheme instanceof WebExConfMonthlyPayByHosts) {
//            checker = new CollisionDetectorWebExConfByHosts();
//        } else if (chargeScheme instanceof WebExConfMonthlyPayByPorts) {
//            checker = new CollisionDetectorWebExConfByPorts();
//        } else if (chargeScheme instanceof WebExConfMonthlyPayByTotalAttendees) {
//            checker = new CollisionDetectorWebExConfByTotalAttendees();
//        } else if (chargeScheme instanceof WebExECPayPerUse) {
//            checker = new CollisionDetectorWebExECPPU();
//        } else if (chargeScheme instanceof WebExECPrepaid) {
//            checker = new CollisionDetectorWebExECPrepaid();
//        } else if (chargeScheme instanceof WebExStorageMonthlyPay) {
//            checker = new CollisionDetectorWebExStorage();
//        } else if (chargeScheme instanceof WebExStorageMonthlyOverflowPay) {
//            checker = new CollisionDetectorWebExStorageOverflow();
//        } else if (chargeScheme instanceof PSTNStandardCharge) {
//            checker = new CollisionDetectorPSTNStandardCharge();
//        } else if (chargeScheme instanceof PSTNMonthlyPacketCharge) {
//            checker = new CollisionDetectorPSTNMonthlyPacket();
//        } else if (chargeScheme instanceof PSTNSinglePacket4MultiSites) {
//            checker = new CollisionDetectorPSTNSinglePacket4MultiSites();
//        } else if (chargeScheme instanceof PSTNSinglePacket4AllSites) {
//            checker = new CollisionDetectorPSTNSinglePacket4AllSites();
//        } else if (chargeScheme instanceof WebExConfMonthlyPayByAH) {
//            checker = new CollisionDetectorWebExConfByAH();
//        } else if (chargeScheme instanceof WebExCmrMonthlyPay) {
//            checker = new CollisionDetectorWebExCmr();
//        }
        if (checker != null) {
            checker.setMe(order);
        }
        return checker;
    }
}
