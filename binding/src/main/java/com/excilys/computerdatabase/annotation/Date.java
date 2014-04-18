package com.excilys.computerdatabase.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.excilys.computerdatabase.validator.DateValidator;

@Target( ElementType.FIELD )
@Documented
@Constraint(validatedBy = DateValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Date {
	String message() default "{date.error}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
