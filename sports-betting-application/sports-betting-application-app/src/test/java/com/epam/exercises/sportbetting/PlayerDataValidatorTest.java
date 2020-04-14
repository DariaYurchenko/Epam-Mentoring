package com.epam.exercises.sportbetting;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.exercises.sportbetting.utils.PlayerDataValidator;

public class PlayerDataValidatorTest {

    @DataProvider(name = "playerDataProvider")
    public Object[][] provideData() {
        return new Object[][] {
            { "1dasha", "The name you've input has incorrect format.", PlayerDataValidator.NAME_REGEX},
            { "@dasha", "The name you've input has incorrect format.", PlayerDataValidator.NAME_REGEX},
            { "123456-1234567", "You have input incorrect account number: the correct format is XXXXXXX-XXXXXXX.", PlayerDataValidator.ACCOUNT_NUMBER_REGEX},
            { "aaaaaaa-aaaaaaa", "You have input incorrect account number: the correct format is XXXXXXX-XXXXXXX.", PlayerDataValidator.ACCOUNT_NUMBER_REGEX},
            { "1990-a1-23", "You have input incorrect date of birth: the correct format is XXXX-XX-XX.", PlayerDataValidator.DATE_OF_BIRTH_REGEX},
            { "1990@01@23", "You have input incorrect date of birth: the correct format is XXXX-XX-XX.", PlayerDataValidator.DATE_OF_BIRTH_REGEX},
            { "100a", "You have input incorrect balance.", PlayerDataValidator.BALANCE_REGEX},
            { "-1000", "You have input incorrect balance.", PlayerDataValidator.BALANCE_REGEX},
            { String.valueOf(Double.MAX_VALUE), "You have input incorrect balance.", PlayerDataValidator.BALANCE_REGEX},
            { "UHA", "You have input incorrect currency.", PlayerDataValidator.CURRENCY_REGEX},
            { "EU3", "You have input incorrect currency.", PlayerDataValidator.CURRENCY_REGEX},
        };
    }

    @Test(dataProvider = "playerDataProvider")
    public void shouldValidatePlayerData(String input, String message, String regex) {
        try {
            PlayerDataValidator.validateInput(input, message, regex);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            assertEquals(message, e.getMessage());
        }

    }
}
