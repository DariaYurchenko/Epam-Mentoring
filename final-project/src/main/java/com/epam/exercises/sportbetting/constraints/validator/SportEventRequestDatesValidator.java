package com.epam.exercises.sportbetting.constraints.validator;

import com.epam.exercises.sportbetting.constraints.GeneralDatesValidator;
import com.epam.exercises.sportbetting.constraints.annos.SportEventRequestDatesAnnotation;
import com.epam.exercises.sportbetting.request.SportEventRequest;
import com.epam.exercises.sportbetting.request.SportEventRequest.BetRequest.OutcomeRequest.OutcomeOddRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class SportEventRequestDatesValidator implements ConstraintValidator<SportEventRequestDatesAnnotation, SportEventRequest> {

    @Override
    public boolean isValid(SportEventRequest request, ConstraintValidatorContext constraintValidatorContext) {
        if(request == null || request.getBets() == null || !checkIfOutcomesNull(request) || !checkIfOddsNull(request)) {
            throw new IllegalArgumentException("anno.null");
        }
        String sportEventDateFrom = request.getStartDate();
        String sportEventDateTo = request.getEndDate();

        boolean sportEventDatesCorrectInterval = GeneralDatesValidator.validateDates(sportEventDateFrom, sportEventDateTo);

        boolean oddDatesCorrectInterval = request.getBets().stream()
                .flatMap(bet -> bet.getOutcomes().stream())
                .flatMap(outcome -> outcome.getOutcomeOdds().stream())
                .allMatch(odd -> checkIfOddDatesCorrect(odd, sportEventDateFrom));

        return sportEventDatesCorrectInterval && oddDatesCorrectInterval;
    }

    private boolean checkIfOddDatesCorrect(OutcomeOddRequest oddRequest, String sportEventDateFrom) {
        if(oddRequest.getFrom() == null || oddRequest.getTo() == null) {
            return false;
        }
        return LocalDate.parse(oddRequest.getTo()).isBefore(LocalDate.parse(sportEventDateFrom))
                && GeneralDatesValidator.validateDates(oddRequest.getFrom(), oddRequest.getTo());
    }

    private boolean checkIfOutcomesNull(SportEventRequest request) {
        return request.getBets().stream()
                .anyMatch(bet -> bet.getOutcomes() != null);
    }

    private boolean checkIfOddsNull(SportEventRequest request) {
        return request.getBets().stream()
                .flatMap(bet -> bet.getOutcomes().stream())
                .anyMatch(outcome -> outcome.getOutcomeOdds() != null);
    }
}
