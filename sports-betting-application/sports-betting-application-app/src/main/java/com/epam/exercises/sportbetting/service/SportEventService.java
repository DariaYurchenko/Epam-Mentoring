package com.epam.exercises.sportbetting.service;

import com.epam.exercises.sportbetting.domain.outcome.Outcome;
import com.epam.exercises.sportbetting.domain.outcome.OutcomeOdd;
import com.epam.exercises.sportbetting.domain.sportevent.SportEvent;
import com.epam.exercises.sportbetting.domain.user.Player;
import com.epam.exercises.sportbetting.domain.wagger.Wager;

import java.util.List;

public interface SportEventService {

    List<OutcomeOdd> getActualOutcomeOdds(SportEvent sportEvent);

    void validateChosenOddBetType(List<Wager> wagers, OutcomeOdd chosenOdd) throws IllegalArgumentException;

    void betOnMoney(Double money, Player player) throws IllegalArgumentException;;

    List<Outcome> getWinningOutcomes(SportEvent sportEvent);

    List<Wager> getPlayerWinningWagers(List<Wager> wagers, List<Outcome> winningOutcomes);

}
