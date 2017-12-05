package wang.huaichao.data.entity.crm;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by Administrator on 8/31/2016.
 */
@Embeddable
public class PstnChargePK implements Serializable {
    private String orderId;
    private int billingPeriod;

    public PstnChargePK() {
    }

    public PstnChargePK(String orderId, int billingPeriod) {
        this.orderId = orderId;
        this.billingPeriod = billingPeriod;
    }

    @Override
    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return (orderId + billingPeriod).hashCode();
    }
}
