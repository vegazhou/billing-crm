package com.kt.spalgorithm.alg.order;

import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.WebExStorageMonthlyOverflowPay;
import com.kt.biz.model.order.OrderBean;
import com.kt.spalgorithm.alg.Alg;
import com.kt.spalgorithm.model.payload.StoragePayload;
import com.kt.spalgorithm.model.payload.WbxPayload;
import org.apache.log4j.Logger;

/**
 * Created by Rickey Zhu on 2017/4/11.
 */
public class AlgStorageOverflow extends Alg {

    private static final Logger LOGGER = Logger.getLogger(AlgStorageOverflow.class);

    @Override
    public void start(OrderBean order, WbxPayload initialState) {
        WebExStorageMonthlyOverflowPay chargeScheme = getChargeScheme(order);
        if (chargeScheme == null) {
            LOGGER.error("charge scheme is not WebExStorageMonthlyOverflowPay");
            return;
        }

        StoragePayload initialStateStoragePayload = initialState.getStoragePayload();
        if(initialStateStoragePayload != null){
            initialStateStoragePayload.setStorageCapacity(initialStateStoragePayload.getStorageCapacity() + chargeScheme.getStorageSize());
            initialStateStoragePayload.setStorageOverage(true);
            initialState.setStoragePayload(initialStateStoragePayload);
        }else {
            StoragePayload payload = initializePayload();
            payload.setStorageCapacity(chargeScheme.getStorageSize());
            initialState.setStoragePayload(payload);
        }
    }

    @Override
    public void terminate(OrderBean order, WbxPayload initialState) {
        WebExStorageMonthlyOverflowPay chargeScheme = getChargeScheme(order);
        if (chargeScheme == null) {
            LOGGER.error("charge scheme is not WebExStorageMonthlyOverflowPay");
            return;
        }

        StoragePayload initialStateStoragePayload = initialState.getStoragePayload();
        if(initialStateStoragePayload != null){
            initialStateStoragePayload.setStorageCapacity(initialStateStoragePayload.getStorageCapacity() - chargeScheme.getStorageSize());
            if(initialStateStoragePayload.getStorageCapacity() <= 1){
                initialStateStoragePayload.setStorageOverage(false);
            }
            initialState.setStoragePayload(initialStateStoragePayload);
        }
    }


    private WebExStorageMonthlyOverflowPay getChargeScheme(OrderBean order) {
        AbstractChargeScheme chargeScheme = order.getChargeScheme();
        if (chargeScheme instanceof WebExStorageMonthlyOverflowPay) {
            return (WebExStorageMonthlyOverflowPay) chargeScheme;
        }
        return null;
    }


    private StoragePayload initializePayload() {
        StoragePayload payload = new StoragePayload();
        payload.setStorageOverage(true);
        return payload;
    }
}
