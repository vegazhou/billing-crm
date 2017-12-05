package com.kt.api.aop;

import com.kt.api.annotation.Auth;
import com.kt.api.annotation.EventLog;
import com.kt.api.common.APIConstants;
import com.kt.api.controller.BaseController;
import com.kt.common.exception.ApiException;
import com.kt.exception.ExceptionKeys;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * ControllerAspect
 */
@Aspect
public class ControllerAspect extends BaseController {
    private static final Logger LOGGER = Logger.getLogger(ControllerAspect.class);

    /**
     * controllerMethodCall
     */
    @Pointcut("execution(public * com.kt.api.controller.*.*(..))")
    public void controllerMethodCall() {
    }

    /**
     * logSuccessEvent
     *
     * @param joinPoint JoinPoint
     */
    @AfterReturning(value = "controllerMethodCall()")
    public void logSuccessEvent(JoinPoint joinPoint) {
        Method method = getControllerMethod(joinPoint);
        EventLog eventLog = method.getAnnotation(EventLog.class);
        if (eventLog != null) {
            HttpServletRequest request = getRequestArg(joinPoint.getArgs());
            String result = getEventStr(eventLog.action(), eventLog.value(), "", request);
            LOGGER.trace(result);
        }
    }

    /**
     * logFailEvent
     *
     * @param joinPoint JoinPoint
     * @param ex        Exception
     */
    @AfterThrowing(value = "controllerMethodCall()", throwing = "ex")
    public void logFailEvent(JoinPoint joinPoint, Exception ex) {
        Method method = getControllerMethod(joinPoint);
        EventLog eventLog = method.getAnnotation(EventLog.class);
        if (eventLog != null && ex instanceof ApiException) {
            HttpServletRequest request = getRequestArg(joinPoint.getArgs());
            String result = getEventStr(eventLog.action(), eventLog.value(),
                    ((ApiException) ex).getErrorKey(), request);
            LOGGER.trace(result);
        }
    }

    private HttpServletRequest getRequestArg(Object[] args) {
        HttpServletRequest request = null;
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                if (arg instanceof HttpServletRequest) {
                    request = (HttpServletRequest) arg;
                    break;
                }
            }
        }
        if (request == null) {
            throw new ApiException(ExceptionKeys.SERVER_INTERNAL_ERROR);
        }
        return request;
    }

    /**
     * authorize
     *
     * @param joinPoint JoinPoint
     */
    @Before(value = "controllerMethodCall()")
    public void authorize(JoinPoint joinPoint) {
        Method method = getControllerMethod(joinPoint);
        Auth auth = method.getAnnotation(Auth.class);
        if (auth != null) {
            HttpServletRequest request = getRequestArg(joinPoint.getArgs());
            String token = request.getHeader(APIConstants.HEADER_NAME_TOKEN);
            verifyToken(token);
            checkPermission(auth.value(), auth.action(), token);
        }
    }

    private Method getControllerMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }
}
