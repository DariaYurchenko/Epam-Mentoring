package com.epam.exercises.sportbetting.service.impl;

import com.epam.exercises.sportbetting.domain.outcome.Outcome;
import com.epam.exercises.sportbetting.domain.outcome.OutcomeOdd;
import com.epam.exercises.sportbetting.service.SportEventService;
import com.epam.exercises.sportbetting.domain.sportevent.SportEvent;
import com.epam.exercises.sportbetting.domain.user.Player;
import com.epam.exercises.sportbetting.domain.wagger.Wager;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.*;

public class SportEventServiceImpl implements SportEventService {

    @Override
    public List<OutcomeOdd> getActualOutcomeOdds(SportEvent sportEvent) {
        return sportEvent.getBets().stream()
            .flatMap(bet -> bet.getOutcomes().stream())
            .flatMap(outcome -> outcome.getOutcomeOdds().stream())
            .filter(this::checkIfCurrentDateIsInInterval)
            .collect(toList());
    }

    private boolean checkIfCurrentDateIsInInterval(OutcomeOdd odd) {
        return LocalDateTime.now().isBefore(odd.getTo()) && LocalDateTime.now().isAfter(odd.getFrom());
    }

    @Override
    public void validateChosenOddBetType(List<Wager> wagers, OutcomeOdd chosenOdd) throws IllegalArgumentException {
        boolean containsOdWithTheSameType = wagers.stream()
            .map(Wager::getOutcomeOdd)
            .anyMatch(odd -> odd.getOutcome().getBet().getType() == chosenOdd.getOutcome().getBet().getType());
        if (containsOdWithTheSameType) {
            throw new IllegalArgumentException("You've already chosen the odd with such type.");
        }
    }

    @Override
    public void betOnMoney(Double money, Player player) throws IllegalArgumentException {
        validateMoneyBetOn(money, player);
        withdrawPlayerMoney(money, player);
    }

    private void validateMoneyBetOn(Double money, Player player) throws IllegalArgumentException {
        if (player.getBalance() < money) {
            throw new IllegalArgumentException("You don't have enough money, your balance is " + player.getBalance() + " " + player.getCurrency() + ".");
        }
    }

    private void withdrawPlayerMoney(Double money, Player player) {
        player.setBalance(player.getBalance() - money);
    }

    @Override
    public List<Wager> getPlayerWinningWagers(List<Wager> playerWagers, List<Outcome> winningOutcomes) {
        return playerWagers.stream()
            .peek(wager -> wager.setProcessed(true))
            .filter(wager -> winningOutcomes.contains(wager.getOutcomeOdd().getOutcome()))
            .peek(wager -> wager.setWin(true))
            .peek(wager -> wager.setWonMoney(wager.getAmount() * wager.getOutcomeOdd().getOdd()))
            .collect(toList());
    }

    @Override
    public List<Outcome> getWinningOutcomes(SportEvent sportEvent) {
        return sportEvent.getBets().stream()
            .map(bet -> bet.getOutcomes().get(new Random().nextInt(bet.getOutcomes().size())))
            .collect(toList());
    }

}
