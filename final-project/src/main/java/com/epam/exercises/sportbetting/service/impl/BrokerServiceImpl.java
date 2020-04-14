package com.epam.exercises.sportbetting.service.impl;

import com.epam.exercises.sportbetting.builder.WinningOutcomesResponseBuilder;
import com.epam.exercises.sportbetting.domain.outcome.Outcome;
import com.epam.exercises.sportbetting.domain.sportevent.SportEvent;
import com.epam.exercises.sportbetting.domain.wagger.Wager;
import com.epam.exercises.sportbetting.repo.SportEventRepo;
import com.epam.exercises.sportbetting.repo.WagersRepo;
import com.epam.exercises.sportbetting.response.WinningOutcomesResponse;
import com.epam.exercises.sportbetting.service.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

@Service
public class BrokerServiceImpl implements BrokerService {
    private SportEventRepo sportEventRepo;
    private WagersRepo wagersRepo;
    private WinningOutcomesResponseBuilder winningOutcomesResponseBuilder;

    @Autowired
    public BrokerServiceImpl(SportEventRepo sportEventRepo, WagersRepo wagersRepo, WinningOutcomesResponseBuilder winningOutcomesResponseBuilder) {
        this.sportEventRepo = sportEventRepo;
        this.wagersRepo = wagersRepo;
        this.winningOutcomesResponseBuilder = winningOutcomesResponseBuilder;
    }

    @Transactional
    @Override
    public WinningOutcomesResponse stageEvent() {
        List<SportEvent> sportEvents = new ArrayList<>();
        sportEventRepo.findAll().forEach(sportEvents::add);

        sportEvents.forEach(this::addWinningOutcomes);

        updateWagers(sportEvents);

        return winningOutcomesResponseBuilder.buildWinningOutcomesResponse(sportEvents);
    }

    private void addWinningOutcomes(SportEvent sportEvent) {
        sportEvent.setWinningOutcomes(generateWinningOutcomes(sportEvent));
        sportEvent.getWinningOutcomes().forEach(outcome -> outcome.setWinningEvent(sportEvent));
    }

    private List<Outcome> generateWinningOutcomes(SportEvent sportEvent) {
        return sportEvent.getBets().stream()
                .map(bet -> bet.getOutcomes().get(new Random().nextInt(bet.getOutcomes().size())))
                .collect(toList());
    }

    private void updateWagers(List<SportEvent> sportEvents) {
        List<Outcome> winningOutcomes = sportEvents.stream()
                .flatMap(event -> event.getWinningOutcomes().stream())
                .collect(toList());

        List<Wager> wagers = new ArrayList<>();
        wagersRepo.findAll().forEach(wagers::add);

        wagers.stream()
                .peek(wager -> wager.setProcessed(true))
                .peek(wager -> wager.setWin(false))
                .filter(wager -> winningOutcomes.contains(wager.getOutcomeOdd().getOutcome()))
                .peek(wager -> wager.setWin(true))
                .forEach(this::updatePlayerBalance);
    }

    private void updatePlayerBalance(Wager wager) {
        wager.getPlayer().setBalance(wager.getPlayer().getBalance() + wager.getAmount() * wager.getOutcomeOdd().getOdd());
    }




}
