package com.kt.validation.annotation;

import com.kt.validation.validator.InOneOfValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Vega Zhou on 2015/11/27.
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {InOneOfValidator.class})
public @interface InOneOf {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] candidates() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        InOneOf[] value();
    }
}
