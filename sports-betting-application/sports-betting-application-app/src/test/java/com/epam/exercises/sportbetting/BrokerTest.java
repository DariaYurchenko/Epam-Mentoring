package com.epam.exercises.sportbetting;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.epam.exercises.sportbetting.dataset.TestDataSet;

import com.epam.exercises.sportbetting.broker.Broker;
import com.epam.exercises.sportbetting.domain.outcome.OutcomeOdd;
import com.epam.exercises.sportbetting.domain.sportevent.SportEvent;
import com.epam.exercises.sportbetting.domain.user.Player;
import com.epam.exercises.sportbetting.domain.wagger.Wager;
import com.epam.exercises.sportbetting.service.SportEventService;

@RunWith(MockitoJUnitRunner.class)
public class BrokerTest {
    private SportEvent sportEvent;
    private Player player;

    @Mock
    SportEventService sportEventService;
    @InjectMocks
    Broker broker;

    @Before
    public void setUp() {
        this.sportEvent = TestDataSet.buildTestSportEvent();
        this.player = TestDataSet.buildTestPlayer();
    }

    @Test
    public void shouldGetActualOutcomeOdds() {
        List<OutcomeOdd> actualOutcomeOdds = TestDataSet.buildTestActualOutcomeOdds(sportEvent);
        when(sportEventService.getActualOutcomeOdds(sportEvent)).thenReturn(actualOutcomeOdds);
        assertEquals(actualOutcomeOdds, broker.getActualOutcomeOdds(sportEvent));
    }

    @Test
    public void shouldValidateChosenOddBetType() {
        List<Wager> playerWagers = TestDataSet.buildTestWagers(sportEvent, player);
        OutcomeOdd chosenOdd = TestDataSet.buildTestOddWithTheSameTypeAsWagersContain(sportEvent);

        broker.validateChosenOddBetType(playerWagers, chosenOdd);

        verify(sportEventService).validateChosenOddBetType(playerWagers, chosenOdd);
    }

    @Test
    public void shouldBetOnMoney() {
        String money = "2000";
        Player player = TestDataSet.buildTestPlayer();

        broker.betOnMoney(player, money);

        verify(sportEventService).betOnMoney(Double.parseDouble(money), player);
    }

    @Test
    public void shouldStageEvent() {
        List<Wager> wagers = TestDataSet.buildTestWagers(sportEvent, player);
        List<Wager> winningWagers = TestDataSet.buildTestWinningWagers(sportEvent, player);
        double playerBalanceBefore = winningWagers.get(0).getPlayer().getBalance();
        double moneyWon = winningWagers.get(0).getWonMoney();

        when(sportEventService.getPlayerWinningWagers(wagers, sportEventService.getWinningOutcomes(sportEvent))).thenReturn(winningWagers);
        broker.stageEvent(sportEvent, wagers);

        double playerBalanceAfter = winningWagers.get(0).getPlayer().getBalance();
        assertEquals(moneyWon, playerBalanceAfter - playerBalanceBefore, 0);
    }

}
