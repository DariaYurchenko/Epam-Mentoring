package com.epam.exercises.sportbetting;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.exercises.sportbetting.dataset.TestDataSet;

import com.epam.exercises.sportbetting.domain.outcome.OutcomeOdd;
import com.epam.exercises.sportbetting.domain.sportevent.SportEvent;
import com.epam.exercises.sportbetting.utils.SportEventDataValidator;

public class SportEventDataValidatorTest {

    @DataProvider(name = "sportEventInputDataProvider")
    public Object[][] provideSportEventInputData() {
        return new Object[][] {
            {"2"},
            {"x"}
        };
    }

    @DataProvider(name = "outcomeOddsInputDataProvider")
    public Object[][] provideOutcomeOddsInputData() {
        return new Object[][] {
            {"You have to input number > 0.", "x"},
            {"You have to input number > 0.", "-1"},
            {"You have to input number < of options' amount.", "10"}
        };
    }

    @DataProvider(name = "moneyInputDataProvider")
    public Object[][] provideMoneyInputData() {
        return new Object[][] {
            {"-200"},
            {"100x"},
            {"0"},
            {"-0"},
            {String.valueOf(Double.MAX_VALUE)}
        };
    }

    @Test(dataProvider = "sportEventInputDataProvider")
    public void shouldValidateSportEventInput(String input) {
        Map<String, SportEvent> sportEventMap = Map.of(
            "1", TestDataSet.buildTestSportEvent()
        );
        TestConsumer.accept(SportEventDataValidator::validateSportEventInput, input, sportEventMap, "There is no such sport event.");
    }

    @Test(dataProvider = "outcomeOddsInputDataProvider")
    public void shouldValidateOutcomeOddsInput(String message, String input) {
        List<OutcomeOdd> odds = TestDataSet.buildTestActualOutcomeOdds(TestDataSet.buildTestSportEvent());
        TestConsumer.accept(SportEventDataValidator::validateOutcomeOddsInput, input, odds, message);
    }

    @Test(dataProvider = "moneyInputDataProvider")
    public void shouldValidateMoneyBetOn(String input) {
        try {
            SportEventDataValidator.validateMoneyBetOn(input);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            assertEquals("You have to input amount of money.", e.getMessage());
        }
    }

}
