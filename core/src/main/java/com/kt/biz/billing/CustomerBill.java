package com.kt.biz.billing;

import com.kt.biz.types.FeeType;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vega Zhou on 2016/4/12.
 */
public class CustomerBill {
    private String customerId;

    private AccountPeriod accountPeriod;

    private Map<FeeType, BigDecimal> fees = new HashMap<>();

    private Map<Key, Detail> details = new HashMap<>();

    public CustomerBill(String customerId, AccountPeriod accountPeriod) {
        this.customerId = customerId;
        this.accountPeriod = accountPeriod;
    }

    public void accumulate(String orderId, FeeType feeType, BigDecimal amount) {
        if (amount.compareTo(new BigDecimal(0)) == 0) {
            return;
        }
        BigDecimal currentAmount = new BigDecimal(0);
        if (fees.containsKey(feeType)) {
            currentAmount = fees.get(feeType);
        }
        currentAmount = currentAmount.add(amount);
        fees.put(feeType, currentAmount);

        countDetailIn(orderId, feeType, amount);
    }


    public void merge(CustomerBill bill) {
        for(Detail detail : bill.getDetails().values()) {
            this.accumulate(detail.getOrderId(), detail.getFeeType(), detail.getAmount());
        }
    }




    private void countDetailIn(String orderId, FeeType feeType, BigDecimal amount) {
        Detail detail = createDetail(orderId, feeType);
        detail.add(amount);
    }

    private Detail createDetail(String orderId, FeeType feeType) {
        Key key = new Key(orderId, feeType);
        if (details.containsKey(key)) {
            return details.get(key);
        } else {
            Detail detail = new Detail(orderId, feeType);
            details.put(key, detail);
            return detail;
        }
    }


    //=========================== GETTER AND SETTER =============================

    public Map<Key, Detail> getDetails() {
        return details;
    }

    public Map<FeeType, BigDecimal> getFees() {
        return fees;
    }

    public AccountPeriod getAccountPeriod() {
        return accountPeriod;
    }

    public String getCustomerId() {
        return customerId;
    }

    private static class Key {
        private String orderId;
        private FeeType feeType;

        public Key(String orderId, FeeType feeType) {
            this.orderId = orderId;
            this.feeType = feeType;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Key) {
                Key against = (Key) obj;
                if (against.feeType == feeType &&
                        StringUtils.equalsIgnoreCase(against.orderId, orderId)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return feeType.toString() + " " + orderId;
        }
    }


    public static final class Detail {
        private String orderId;

        private BigDecimal amount = new BigDecimal(0);

        private FeeType feeType;

        public Detail(String orderId, FeeType feeType) {
            this.orderId = orderId;
            this.feeType = feeType;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public FeeType getFeeType() {
            return feeType;
        }

        public void setFeeType(FeeType feeType) {
            this.feeType = feeType;
        }

        public void add(BigDecimal amount) {
            this.amount = this.amount.add(amount);
        }

        @Override
        public String toString() {
            return amount.toString();
        }
    }
}
