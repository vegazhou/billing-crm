package com.kt.biz.model.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Vega Zhou on 2016/3/10.
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface ModelAttribute {

    String value();

    AttributeType type() default AttributeType.STRING;

    boolean changeable() default true;
}
