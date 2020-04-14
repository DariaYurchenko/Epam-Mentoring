package com.epam.exercises.sportbetting.dataset;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import com.epam.exercises.sportbetting.domain.bet.Bet;
import com.epam.exercises.sportbetting.domain.bet.BetType;
import com.epam.exercises.sportbetting.domain.outcome.Outcome;
import com.epam.exercises.sportbetting.domain.outcome.OutcomeOdd;
import com.epam.exercises.sportbetting.domain.sportevent.SportEvent;
import com.epam.exercises.sportbetting.domain.sportevent.TennisSportEvent;
import com.epam.exercises.sportbetting.domain.user.Player;
import com.epam.exercises.sportbetting.domain.wagger.Wager;

public class TestDataSet {

    public static Player buildTestPlayer() {
        return Player.newInstance()
            .withName("XXX")
            .withAccountNumber("1234567-1234567")
            .withCurrency("UAH")
            .withDateOfBirth(LocalDate.parse("1990-01-01"))
            .withBalance(1000.0)
            .build();
    }

    public static SportEvent buildTestSportEvent() {
        SportEvent testEvent = TennisSportEvent.newInstance()
            .withTitle("Test event")
            .withStartDate(LocalDateTime.of(2020, Month.APRIL, 20, 19, 0, 0))
            .withEndDate(LocalDateTime.of(2020, Month.APRIL, 20, 21, 0, 0))
            .build();

        OutcomeOdd testOdd1 = OutcomeOdd.newInstance()
            .withOdd(1.5)
            .withDateFrom(LocalDateTime.of(2019, Month.DECEMBER, 22, 0, 0, 0))
            .withDateTo(LocalDateTime.now().plusDays(1))
            .build();

        List<OutcomeOdd> testOdds1 = List.of(testOdd1);
        Outcome testOutcome1 = new Outcome("Test outcome 1", testOdds1);
        testOdd1.setOutcome(testOutcome1);

        OutcomeOdd testOdd2 = OutcomeOdd.newInstance()
            .withOdd(1.75)
            .withDateFrom(LocalDateTime.of(2019, Month.DECEMBER, 22, 0, 0, 0))
            .withDateTo(LocalDateTime.now().plusDays(1))
            .build();

        List<OutcomeOdd> testOdds2 = List.of(testOdd2);
        Outcome testOutcome2 = new Outcome("Test outcome 2", testOdds2);
        testOdd2.setOutcome(testOutcome2);

        List<Outcome> winnersTestOutcomes = List.of(testOutcome2, testOutcome1);
        Bet testWinnerBet = Bet.newInstance()
            .withDescription("Test winner bet")
            .withOutcomes(winnersTestOutcomes)
            .withType(BetType.WINNER)
            .withSportEvent(testEvent)
            .build();
        testOutcome2.setBet(testWinnerBet);
        testOutcome1.setBet(testWinnerBet);

        OutcomeOdd testOdd3 = OutcomeOdd.newInstance()
            .withOdd(2.75)
            .withDateFrom(LocalDateTime.of(2019, Month.DECEMBER, 22, 0, 0, 0))
            .withDateTo(LocalDateTime.now().plusDays(1))
            .build();

        List<OutcomeOdd> testOdds3 = List.of(testOdd3);
        Outcome testOutcome3 = new Outcome("Test Outcome 3", testOdds3);
        testOdd3.setOutcome(testOutcome3);

        OutcomeOdd testOdd4 = OutcomeOdd.newInstance()
            .withOdd(3.25)
            .withDateFrom(LocalDateTime.of(2019, Month.DECEMBER, 22, 0, 0, 0))
            .withDateTo(LocalDateTime.now().plusDays(1))
            .build();

        List<OutcomeOdd> testOdds4 = List.of(testOdd4);
        Outcome testOutcome4 = new Outcome("Test Outcome 4", testOdds4);
        testOdd4.setOutcome(testOutcome4);

        List<Outcome> scoreOutcomes = List.of(testOutcome4, testOutcome3);
        Bet testScoreBet = Bet.newInstance()
            .withDescription("Test score bet")
            .withOutcomes(scoreOutcomes)
            .withType(BetType.PLAYER_SCORE)
            .withSportEvent(testEvent)
            .build();
        testOutcome4.setBet(testScoreBet);
        testOutcome3.setBet(testScoreBet);

        List<Bet> tennisBets = List.of(testWinnerBet, testScoreBet);
        testEvent.setBets(tennisBets);
        return testEvent;
    }

    public static List<Wager> buildTestWagers(SportEvent sportEvent, Player player) {
        return List.of(
            Wager.newInstance()
                .withPlayer(player)
                .withAmount(100.0)
                .withCurrency("UAH")
                .withTimeStamp(LocalDateTime.now().minusDays(1))
                .withOutcomeOdd(sportEvent.getBets().get(0).getOutcomes().get(0).getOutcomeOdds().get(0))
                .build(),
            Wager.newInstance()
                .withPlayer(player)
                .withAmount(200.0)
                .withCurrency("UAH")
                .withTimeStamp(LocalDateTime.now().minusDays(1))
                .withOutcomeOdd(sportEvent.getBets().get(1).getOutcomes().get(1).getOutcomeOdds().get(0))
                .build()
        );
    }

    public static List<Wager> buildTestWinningWagers(SportEvent sportEvent, Player player) {
        return List.of(
            Wager.newInstance()
                .withPlayer(player)
                .withAmount(100.0)
                .withWin(true)
                .withProcessed(true)
                .withWonMoney(175.0)
                .withCurrency("UAH")
                .withTimeStamp(LocalDateTime.now().minusDays(1))
                .withOutcomeOdd(sportEvent.getBets().get(0).getOutcomes().get(0).getOutcomeOdds().get(0))
                .build()
        );
    }

    public static OutcomeOdd buildTestOddWithTheSameTypeAsWagersContain(SportEvent sportEvent) {
        return sportEvent.getBets().get(0).getOutcomes().get(1).getOutcomeOdds().get(0);
    }

    public static List<Outcome> buildTestWinningOutcomes(SportEvent sportEvent) {
        return List.of(sportEvent.getBets().get(0).getOutcomes().get(0),
            sportEvent.getBets().get(1).getOutcomes().get(0));
    }

    public static List<OutcomeOdd> buildTestActualOutcomeOdds(SportEvent sportEvent) {
        return sportEvent.getBets().stream()
            .flatMap(bet -> bet.getOutcomes().stream())
            .flatMap(outcome -> outcome.getOutcomeOdds().stream())
            .collect(Collectors.toList());
    }

}
