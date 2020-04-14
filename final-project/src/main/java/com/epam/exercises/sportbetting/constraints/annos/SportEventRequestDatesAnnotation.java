package com.epam.exercises.sportbetting.constraints.annos;

import com.epam.exercises.sportbetting.constraints.validator.SportEventRequestDatesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SportEventRequestDatesValidator.class)
public @interface SportEventRequestDatesAnnotation {

    String message() default "invalid.dates";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
