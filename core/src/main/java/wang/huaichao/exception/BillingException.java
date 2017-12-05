package wang.huaichao.exception;

import com.kt.exception.WafException;

/**
 * Created by Administrator on 8/31/2016.
 */
public class BillingException extends WafException {
    @Override
    public String getKey() {
        return "pstn_bill_not_found";
    }

    public BillingException() {
        super();
    }

    public BillingException(String message) {
        super(message);
    }

    public BillingException(String message, Throwable cause) {
        super(message, cause);
    }

    public BillingException(Throwable cause) {
        super(cause);
    }
}
