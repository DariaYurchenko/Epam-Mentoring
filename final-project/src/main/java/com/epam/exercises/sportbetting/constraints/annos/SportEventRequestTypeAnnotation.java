package com.epam.exercises.sportbetting.constraints.annos;

import com.epam.exercises.sportbetting.constraints.validator.SportEventRequestTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SportEventRequestTypeValidator.class)
public @interface SportEventRequestTypeAnnotation {

    String message() default "invalid.type";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
