package com.kt.spalgorithm.alg.order;

import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.WebExStorageMonthlyPay;
import com.kt.biz.model.order.OrderBean;
import com.kt.spalgorithm.alg.Alg;
import com.kt.spalgorithm.model.payload.StoragePayload;
import com.kt.spalgorithm.model.payload.WbxPayload;
import org.apache.log4j.Logger;

/**
 * Created by Vega Zhou on 2016/3/28.
 */
public class AlgStorage extends Alg {

    private static final Logger LOGGER = Logger.getLogger(AlgStorage.class);

    @Override
    public void start(OrderBean order, WbxPayload initialState) {
        WebExStorageMonthlyPay chargeScheme = getChargeScheme(order);
        if (chargeScheme == null) {
            LOGGER.error("charge scheme is not WebExStorageMonthlyPay");
            return;
        }

        StoragePayload initialStateStoragePayload = initialState.getStoragePayload();
        if(initialStateStoragePayload != null){
            initialStateStoragePayload.setStorageCapacity(initialStateStoragePayload.getStorageCapacity() + chargeScheme.getStorageSize());
            initialStateStoragePayload.setStorageOverage(false);
            initialState.setStoragePayload(initialStateStoragePayload);
        }else {
            StoragePayload payload = initializePayload();
            payload.setStorageCapacity(chargeScheme.getStorageSize());
            initialState.setStoragePayload(payload);
        }
    }

    @Override
    public void terminate(OrderBean order, WbxPayload initialState) {
        WebExStorageMonthlyPay chargeScheme = getChargeScheme(order);
        if (chargeScheme == null) {
            LOGGER.error("charge scheme is not WebExStorageMonthlyPay");
            return;
        }

        StoragePayload initialStateStoragePayload = initialState.getStoragePayload();
        if(initialStateStoragePayload != null){
            initialStateStoragePayload.setStorageCapacity(initialStateStoragePayload.getStorageCapacity() - chargeScheme.getStorageSize());
            initialState.setStoragePayload(initialStateStoragePayload);
        }
    }


    private WebExStorageMonthlyPay getChargeScheme(OrderBean order) {
        AbstractChargeScheme chargeScheme = order.getChargeScheme();
        if (chargeScheme instanceof WebExStorageMonthlyPay) {
            return (WebExStorageMonthlyPay) chargeScheme;
        }
        return null;
    }


    private StoragePayload initializePayload() {
        StoragePayload payload = new StoragePayload();
        payload.setStorageOverage(false);
        return payload;
    }
}
