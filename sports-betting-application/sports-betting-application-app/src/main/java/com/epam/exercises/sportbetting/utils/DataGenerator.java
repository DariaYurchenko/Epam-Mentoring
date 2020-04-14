package com.epam.exercises.sportbetting.utils;

import com.epam.exercises.sportbetting.domain.bet.Bet;
import com.epam.exercises.sportbetting.domain.bet.BetType;
import com.epam.exercises.sportbetting.domain.outcome.Outcome;
import com.epam.exercises.sportbetting.domain.outcome.OutcomeOdd;
import com.epam.exercises.sportbetting.domain.sportevent.SportEvent;
import com.epam.exercises.sportbetting.domain.sportevent.TennisSportEvent;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;

public final class DataGenerator {

    private DataGenerator() {

    }

    public static Map<String, SportEvent> generateData() {
        SportEvent tennisEvent = TennisSportEvent.newInstance()
            .withTitle("Rafael Nadal vs. Alexander Zverev, Indian Wells 4th Round.")
            .withStartDate(LocalDateTime.of(2020, Month.APRIL, 20, 19, 0, 0))
            .withEndDate(LocalDateTime.of(2020, Month.APRIL, 20, 21, 0, 0))
            .build();

        OutcomeOdd nadalWinnerOdd1 = OutcomeOdd.newInstance()
            .withOdd(1.5)
            .withDateFrom(LocalDateTime.of(2019, Month.DECEMBER, 22, 0, 0, 0))
            .withDateTo(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0, 0))
            .build();
        OutcomeOdd nadalWinnerOdd2 = OutcomeOdd.newInstance()
            .withOdd(1.05)
            .withDateFrom(LocalDateTime.of(2020, Month.JANUARY, 2, 0, 0, 0))
            .withDateTo(LocalDateTime.of(2020, Month.JANUARY, 22, 0, 0, 0))
            .build();

        List<OutcomeOdd> nadalWinnerOdds = List.of(nadalWinnerOdd1, nadalWinnerOdd2);
        Outcome nadalWinner = new Outcome("The winner is Rafael Nadal.", nadalWinnerOdds);
        nadalWinnerOdd1.setOutcome(nadalWinner);
        nadalWinnerOdd2.setOutcome(nadalWinner);

        OutcomeOdd zverevWinnerOdd1 = OutcomeOdd.newInstance()
            .withOdd(1.75)
            .withDateFrom(LocalDateTime.of(2019, Month.DECEMBER, 22, 0, 0, 0))
            .withDateTo(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0, 0))
            .build();
        OutcomeOdd zverevWinnerOdd2 = OutcomeOdd.newInstance()
            .withOdd(1.25)
            .withDateFrom(LocalDateTime.of(2020, Month.JANUARY, 2, 0, 0, 0))
            .withDateTo(LocalDateTime.of(2020, Month.JANUARY, 22, 0, 0, 0))
            .build();

        List<OutcomeOdd> zverevWinnerOdds = List.of(zverevWinnerOdd1, zverevWinnerOdd2);
        Outcome zverevWinner = new Outcome("The winner is Alexander Zverev.", zverevWinnerOdds);
        zverevWinnerOdd1.setOutcome(zverevWinner);
        zverevWinnerOdd2.setOutcome(zverevWinner);

        List<Outcome> winnersOutcomes = List.of(zverevWinner, nadalWinner);
        Bet winnerBet = Bet.newInstance()
            .withDescription("Rafael Nadal vs. Alexander Zverev, Indian Wells 4th Round, bet on the winner.")
            .withOutcomes(winnersOutcomes)
            .withType(BetType.WINNER)
            .withSportEvent(tennisEvent)
            .build();
        zverevWinner.setBet(winnerBet);
        nadalWinner.setBet(winnerBet);

        OutcomeOdd nadalScoreOdd1 = OutcomeOdd.newInstance()
            .withOdd(2.75)
            .withDateFrom(LocalDateTime.of(2019, Month.DECEMBER, 22, 0, 0, 0))
            .withDateTo(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0, 0))
            .build();
        OutcomeOdd nadalScoreOdd2 = OutcomeOdd.newInstance()
            .withOdd(2.0)
            .withDateFrom(LocalDateTime.of(2020, Month.JANUARY, 2, 0, 0, 0))
            .withDateTo(LocalDateTime.of(2020, Month.JANUARY, 22, 0, 0, 0))
            .build();

        List<OutcomeOdd> nadalScoreOdds = List.of(nadalScoreOdd1, nadalScoreOdd2);
        Outcome nadalScore = new Outcome("Rafael Nadal's score is 2.", nadalScoreOdds);
        nadalScoreOdd1.setOutcome(nadalScore);
        nadalScoreOdd2.setOutcome(nadalScore);

        OutcomeOdd zverevScoreOdd1 = OutcomeOdd.newInstance()
            .withOdd(3.25)
            .withDateFrom(LocalDateTime.of(2019, Month.DECEMBER, 22, 0, 0, 0))
            .withDateTo(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0, 0))
            .build();

        OutcomeOdd zverevScoreOdd2 = OutcomeOdd.newInstance()
            .withOdd(2.25)
            .withDateFrom(LocalDateTime.of(2020, Month.JANUARY, 2, 0, 0, 0))
            .withDateTo(LocalDateTime.of(2020, Month.JANUARY, 22, 0, 0, 0))
            .build();

        List<OutcomeOdd> zverevScoreOdds = List.of(zverevScoreOdd1, zverevScoreOdd2);
        Outcome zverevScore = new Outcome("Alexander Zverev's score is 2.", zverevScoreOdds);
        zverevScoreOdd1.setOutcome(zverevScore);
        zverevScoreOdd2.setOutcome(zverevScore);

        List<Outcome> scoreOutcomes = List.of(zverevScore, nadalScore);
        Bet scoreBet = Bet.newInstance()
            .withDescription("Rafael Nadal vs. Alexander Zverev, Indian Wells 4th Round, bet on the score.")
            .withOutcomes(scoreOutcomes)
            .withType(BetType.PLAYER_SCORE)
            .withSportEvent(tennisEvent)
            .build();
        zverevScore.setBet(scoreBet);
        nadalScore.setBet(scoreBet);


        List<Bet> tennisBets = List.of(winnerBet, scoreBet);
        tennisEvent.setBets(tennisBets);

        return Map.ofEntries(
            Map.entry("1", tennisEvent)
        );
    }
}
