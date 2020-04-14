package com.epam.exercises.sportbetting.utils;

import com.epam.exercises.sportbetting.domain.outcome.OutcomeOdd;
import com.epam.exercises.sportbetting.domain.sportevent.SportEvent;

import java.util.List;
import java.util.Map;

public final class SportEventDataValidator {
    private static final String DIGITS_REGEX = "^[1-9]\\d+";

    private SportEventDataValidator() {
    }

    public static void validateSportEventInput(String event, Map<String, SportEvent> sportEvents) {
        if (!sportEvents.containsKey(event)) {
            throw new IllegalArgumentException("There is no such sport event.");
        }
    }

    public static void validateOutcomeOddsInput(String odd, List<OutcomeOdd> odds) {
        if (!odd.matches(DIGITS_REGEX)) {
            throw new IllegalArgumentException("You have to input number > 0.");
        }
        if (Integer.parseInt(odd) > odds.size()) {
            throw new IllegalArgumentException("You have to input number < of options' amount.");
        }
    }

    public static void validateMoneyBetOn(String money) {
        if (!money.matches(DIGITS_REGEX) || Double.parseDouble(money) > 1_000_000_000) {
            throw new IllegalArgumentException("You have to input amount of money <= 1000000000.");
        }
    }

}
