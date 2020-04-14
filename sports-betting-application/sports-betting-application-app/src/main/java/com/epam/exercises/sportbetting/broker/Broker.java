package com.epam.exercises.sportbetting.broker;

import java.util.List;
import java.util.Map;

import com.epam.exercises.sportbetting.domain.outcome.Outcome;
import com.epam.exercises.sportbetting.domain.outcome.OutcomeOdd;
import com.epam.exercises.sportbetting.domain.sportevent.SportEvent;
import com.epam.exercises.sportbetting.domain.user.Player;
import com.epam.exercises.sportbetting.domain.wagger.Wager;
import com.epam.exercises.sportbetting.service.SportEventService;

public class Broker {
    private SportEventService sportEventService;
    private Map<String, SportEvent> sportEvents;

    public Broker(SportEventService sportEventService, Map<String, SportEvent> sportEvents) {
        this.sportEventService = sportEventService;
        this.sportEvents = sportEvents;
    }

    public Map<String, SportEvent> getSportEvents() {
        return sportEvents;
    }

    public List<OutcomeOdd> getActualOutcomeOdds(SportEvent sportEvent) {
        return sportEventService.getActualOutcomeOdds(sportEvent);
    }

    public void validateChosenOddBetType(List<Wager> wagers, OutcomeOdd chosenOdd) throws IllegalArgumentException {
        sportEventService.validateChosenOddBetType(wagers, chosenOdd);
    }

    public void betOnMoney(Player player, String money) throws IllegalArgumentException {
        Double moneyBetOn = Double.parseDouble(money);
        sportEventService.betOnMoney(moneyBetOn, player);
    }

    public void stageEvent(SportEvent sportEvent, List<Wager> playerWagers) {
        List<Outcome> winningOutcomes = sportEventService.getWinningOutcomes(sportEvent);
        sportEvent.setWinningOutcomes(winningOutcomes);
        List<Wager> winningWagers = sportEventService.getPlayerWinningWagers(playerWagers, winningOutcomes);
        winningWagers.forEach(wager -> wager.getPlayer().setBalance(wager.getPlayer().getBalance() + wager.getWonMoney()));
    }

}
