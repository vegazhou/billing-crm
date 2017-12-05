# billing service interface


## Setup

Inject this Spring service `wang.huaichao.data.service.BillingService`.

## calculating PSTN bill

### calculating by customer

```
    public void calculatingPstnFeeByCustomer(int billPeriod, String customerId)
        throws ParseException, IOException;
```

### batch calculating
```
    public void batchCalculatingPstnFee(String billPeriod)
        throws IOException, ParseException;
```

## fetch bill

### by standard charge order id

```
    public float getPstnFeeByBillingPeriodAndOrderId(String orderId, int billingPeriod)
        throws BillingException;
```

### by customer id
```
    public float getPstnFeeByBillingPeriodAndCustomerId(String customerId, int billingPeriod)
        throws BillingException;
```