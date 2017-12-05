package com.kt.api.annotation;

import java.lang.annotation.*;

/**
 * EventLog
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventLog {
    String value() default "";
    int action() default 0;
}
