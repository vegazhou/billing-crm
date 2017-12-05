package com.kt.api.annotation;

import java.lang.annotation.*;

/**
 * Auth
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
    String value() default "";
    String action() default "";
    String type() default "";
}
