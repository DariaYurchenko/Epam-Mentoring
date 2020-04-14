package com.epam.exercises.sportbetting.constraints;

import java.time.LocalDate;

public final class GeneralDatesValidator {
    private static final String DATE_REGEX = "[\\d]{4}[-][\\d]{2}[-][\\d]{2}";

    private GeneralDatesValidator() {

    }

    public static boolean validateDates(String dateFrom, String dateTo) {
        if(dateFrom == null || dateTo == null) {
            return false;
        }
        if(!dateFrom.matches(DATE_REGEX) || !dateTo.matches(DATE_REGEX)) {
            return false;
        }

        LocalDate from = LocalDate.parse(dateFrom);
        LocalDate to = LocalDate.parse(dateTo);

        return !from.isAfter(to) && from.isAfter(LocalDate.now());
    }
}
