package com.epam.exercises.sportbetting.constraints.validator;

import com.epam.exercises.sportbetting.constraints.annos.SportEventRequestTypeAnnotation;
import com.epam.exercises.sportbetting.domain.bet.BetType;
import com.epam.exercises.sportbetting.domain.sportevent.SportEventType;
import com.epam.exercises.sportbetting.request.SportEventRequest;
import com.epam.exercises.sportbetting.request.SportEventRequest.BetRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SportEventRequestTypeValidator implements ConstraintValidator<SportEventRequestTypeAnnotation, SportEventRequest> {
    private static final String TYPE_REGEX = "[\\d]+";

    @Override
    public boolean isValid(SportEventRequest request, ConstraintValidatorContext constraintValidatorContext) {
        if(request == null) {
            throw new IllegalArgumentException("anno.null");
        }
        return validateSportEventType(request);
    }

    private boolean validateSportEventType(SportEventRequest request) {
        String type = request.getSportEventType();
        if(checkRegexNotMatching(type)) {
            return false;
        }
        int typesAmount = SportEventType.values().length;
        return Integer.parseInt(type) < typesAmount && validateBetType(request);
    }

    private boolean validateBetType(SportEventRequest request) {
        int typesAmount = BetType.values().length;
        return request.getBets().stream()
                .map(BetRequest::getType)
                .allMatch(type -> !checkRegexNotMatching(type) && Integer.parseInt(type) < typesAmount);
    }

    private boolean checkRegexNotMatching(String type) {
        return type == null || !type.matches(TYPE_REGEX);
    }
}
