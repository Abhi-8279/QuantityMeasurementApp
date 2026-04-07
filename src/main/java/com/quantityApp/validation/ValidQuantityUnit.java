package com.quantityApp.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = QuantityUnitValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidQuantityUnit {

    String message() default "Unit must be valid for the specified measurement type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
