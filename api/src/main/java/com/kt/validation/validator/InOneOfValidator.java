package com.kt.validation.validator;

import com.kt.validation.annotation.InOneOf;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Vega Zhou on 2015/11/27.
 */
public class InOneOfValidator implements ConstraintValidator<InOneOf, String> {

    String[] candidates;

    public void initialize(InOneOf constraintAnnotation) {
        candidates = constraintAnnotation.candidates();
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (candidates == null || candidates.length < 1) {
            return false;
        }

        for (String candidate : candidates) {
            if (candidate.equals(value)) {
                return true;
            }
        }
        return false;
    }
}