package wang.huaichao.data.entity.crm;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

import static wang.huaichao.data.entity.crm.BillingProgress.BATCH_CUSTOMER_ID;

/**
 * Created by Administrator on 9/20/2016.
 */
@Embeddable
public class BillingProgressPK implements Serializable {
    private int billingPeriod;
    private String customerId = BATCH_CUSTOMER_ID;
    private BillingProgressType type;

    public enum BillingProgressType {
        BATCH_PDF_GENERATION,
        BATCH_PSTN_FEE_CALCULATION,
        BATCH_PDF_BILL_MAILING,
        SINGLE_PDF_GENERATION,
        SINGLE_PSTN_FEE_CALCULATION,
        SINGLE_PDF_BILL_MAILING
    }

    public BillingProgressPK() {
    }

    public BillingProgressPK(String customerId, int billingPeriod, BillingProgressType type) {
        this.billingPeriod = billingPeriod;
        this.customerId = customerId;
        this.type = type;
    }

    public int getBillingPeriod() {
        return billingPeriod;
    }

    public void setBillingPeriod(int billingPeriod) {
        this.billingPeriod = billingPeriod;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BillingProgressType getType() {
        return type;
    }

    public void setType(BillingProgressType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return (billingPeriod + customerId + type.name()).hashCode();
    }
}
