package com.epam.exercises.sportbetting.constraints.validator;

import com.epam.exercises.sportbetting.constraints.GeneralDatesValidator;
import com.epam.exercises.sportbetting.constraints.annos.OddRequestDatesAnnotation;
import com.epam.exercises.sportbetting.request.OddRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OddRequestDatesValidator implements ConstraintValidator<OddRequestDatesAnnotation, OddRequest> {

    @Override
    public boolean isValid(OddRequest oddRequest, ConstraintValidatorContext constraintValidatorContext) {
        if(oddRequest == null) {
            throw new IllegalArgumentException("anno.null");
        }
        String dateFrom = oddRequest.getFrom();
        String dateTo = oddRequest.getTo();
        return GeneralDatesValidator.validateDates(dateFrom, dateTo);
    }

}
