package com.suncorp.banking.application.validation.annonation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.suncorp.banking.application.validation.validator.ConditionalValidator;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ConditionalValidator.class })
public @interface Conditional {
	String message() default "Filed is required";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
