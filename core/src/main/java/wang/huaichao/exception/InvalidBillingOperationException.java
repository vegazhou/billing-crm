package wang.huaichao.exception;

import com.kt.exception.WafException;

/**
 * Created by Administrator on 9/1/2016.
 */
public class InvalidBillingOperationException extends WafException {
    @Override
    public String getKey() {
        return "invalid_billing_operation";
    }

    public InvalidBillingOperationException() {
        super();
    }

    public InvalidBillingOperationException(String message) {
        super(message);
    }

    public InvalidBillingOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBillingOperationException(Throwable cause) {
        super(cause);
    }
}
