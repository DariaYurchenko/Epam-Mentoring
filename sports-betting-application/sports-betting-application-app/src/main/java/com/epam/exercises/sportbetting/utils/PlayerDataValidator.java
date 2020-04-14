package com.epam.exercises.sportbetting.utils;

public final class PlayerDataValidator {
    public static final String NAME_REGEX = "[a-zA-Z]+";
    public static final String ACCOUNT_NUMBER_REGEX = "[\\d]{7}[-][\\d]{7}";
    public static final String DATE_OF_BIRTH_REGEX = "[\\d]{4}[-][\\d]{2}[-][\\d]{2}";
    public static final String BALANCE_REGEX = "[123456789][\\d]+";
    public static final String CURRENCY_REGEX = "UAH|EUR|USD";

    private PlayerDataValidator() {

    }

    public static void validateInput(String input, String message, String regex) {
        if (!input.matches(regex) || Double.parseDouble(input) > 1_000_000_000) {
            throw new IllegalArgumentException(message);
        }
    }
}
