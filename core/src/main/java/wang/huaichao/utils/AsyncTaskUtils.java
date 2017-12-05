package wang.huaichao.utils;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.concurrent.Future;

/**
 * Created by Administrator on 9/21/2016.
 */
public class AsyncTaskUtils {
    public static final int SUCCEEDED_CODE = 0;
    public static final int FAILED_CODE = 1;
    public static final int CANCELED_CODE = 2;

    public static final Future<Integer> SUCCEEDED = new AsyncResult<>(SUCCEEDED_CODE);
    public static final Future<Integer> FAILED = new AsyncResult<>(FAILED_CODE);
    public static final Future<Integer> CANCELED = new AsyncResult<>(CANCELED_CODE);
}
