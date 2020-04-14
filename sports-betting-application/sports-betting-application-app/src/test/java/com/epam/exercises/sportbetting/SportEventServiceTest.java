package com.epam.exercises.sportbetting;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.testng.annotations.DataProvider;

import com.epam.exercises.sportbetting.dataset.TestDataSet;

import com.epam.exercises.sportbetting.domain.outcome.Outcome;
import com.epam.exercises.sportbetting.domain.outcome.OutcomeOdd;
import com.epam.exercises.sportbetting.domain.sportevent.SportEvent;
import com.epam.exercises.sportbetting.domain.user.Player;
import com.epam.exercises.sportbetting.domain.wagger.Wager;
import com.epam.exercises.sportbetting.service.SportEventService;
import com.epam.exercises.sportbetting.service.impl.SportEventServiceImpl;

public class SportEventServiceTest {
    private SportEventService sportEventService = new SportEventServiceImpl();
    private SportEvent sportEvent;
    private Player player;

    @Before
    public void setUp() {
        this.sportEvent = TestDataSet.buildTestSportEvent();
        this.player = TestDataSet.buildTestPlayer();
    }

    @DataProvider
    public Object[][] provideMoneyToWithdraw() {
        return new Object[][] {
            {"You have to input number > 0.", "x"},
            {"You have to input number > 0.", "-1"},
            {"You have to input number < of options' amount.", "10"}
        };
    }

    @Test
    public void shouldBetOnMoneyThrowException() {
        Double moneyBetOn = player.getBalance() + 1000.0;
        TestConsumer.accept(sportEventService::betOnMoney, moneyBetOn, player, "You don't have enough money, your balance is " + player.getBalance() + " " + player.getCurrency() + ".");
    }

    @Test
    public void shouldWithdrawPlayerMoney() {
        Double moneyToWithdraw = 100.0;
        Double expected = player.getBalance() - moneyToWithdraw;

        sportEventService.betOnMoney(moneyToWithdraw, player);
        Double actual = player.getBalance();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldValidateChosenOddBetType() {
        List<Wager> playerWagers = TestDataSet.buildTestWagers(sportEvent, player);
        OutcomeOdd chosenOdd = TestDataSet.buildTestOddWithTheSameTypeAsWagersContain(sportEvent);
        TestConsumer.accept(sportEventService::validateChosenOddBetType, playerWagers, chosenOdd, "You've already chosen the odd with such type.");
    }

    @Test
    public void shouldGetPlayerWinningWagers() {
        List<Wager> playerWagers = TestDataSet.buildTestWagers(sportEvent, player);
        List<Outcome> winningOutcomes = TestDataSet.buildTestWinningOutcomes(sportEvent);

        List<Wager> expected = TestDataSet.buildTestWinningWagers(sportEvent, player);
        List<Wager> actual = sportEventService.getPlayerWinningWagers(playerWagers, winningOutcomes);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldGetActualOutcomeOdds() {
        List<OutcomeOdd> expected = TestDataSet.buildTestActualOutcomeOdds(sportEvent);
        List<OutcomeOdd> actual = sportEventService.getActualOutcomeOdds(sportEvent);
        assertEquals(expected, actual);
    }


}
