package com.epam.exercises.sportbetting.service.impl;

import com.epam.exercises.sportbetting.builder.BetResponseBuilder;
import com.epam.exercises.sportbetting.builder.SportEventBuilder;
import com.epam.exercises.sportbetting.builder.SportEventResponseBuilder;
import com.epam.exercises.sportbetting.domain.outcome.Outcome;
import com.epam.exercises.sportbetting.domain.outcome.OutcomeOdd;
import com.epam.exercises.sportbetting.domain.sportevent.SportEvent;
import com.epam.exercises.sportbetting.repo.OutcomesRepo;
import com.epam.exercises.sportbetting.repo.SportEventRepo;
import com.epam.exercises.sportbetting.request.OddRequest;
import com.epam.exercises.sportbetting.request.SportEventRequest;
import com.epam.exercises.sportbetting.response.BetResponse;
import com.epam.exercises.sportbetting.response.SportEventsResponse;
import com.epam.exercises.sportbetting.service.SportEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SportEventServiceImpl implements SportEventService {
    private SportEventRepo sportEventRepo;
    private OutcomesRepo outcomesRepo;
    private SportEventBuilder sportEventBuilder;
    private BetResponseBuilder betResponseBuilder;
    private SportEventResponseBuilder sportEventResponseBuilder;

    @Autowired
    public SportEventServiceImpl(SportEventRepo sportEventRepo, OutcomesRepo outcomesRepo, SportEventBuilder sportEventBuilder, SportEventResponseBuilder sportEventResponseBuilder, BetResponseBuilder betResponseBuilder) {
        this.sportEventRepo = sportEventRepo;
        this.outcomesRepo = outcomesRepo;
        this.sportEventBuilder = sportEventBuilder;
        this.betResponseBuilder = betResponseBuilder;
        this.sportEventResponseBuilder = sportEventResponseBuilder;
    }

    @Transactional
    @Override
    public List<SportEventsResponse> getSportEvents() {
        List<SportEvent> sportEvents = new ArrayList<>();
        sportEventRepo.findAll().forEach(sportEvents::add);
        return sportEventResponseBuilder.buildSportEventResponseList(sportEvents);
    }

    @Transactional
    @Override
    public BetResponse getBets(Integer id) {
        SportEvent sportEvent = sportEventRepo.findById(id).orElseThrow(() -> new NoSuchElementException("invalid.sport.event"));
        return betResponseBuilder.buildBetResponse(sportEvent);
    }

    @Transactional
    @Override
    public void saveSportEvent(SportEventRequest request) {
        sportEventRepo.save(sportEventBuilder.buildSportEvent(request));
    }

    @Transactional
    @Override
    public void saveOutcomeOdd(OddRequest oddRequest) {
        Outcome outcome = outcomesRepo.findById(oddRequest.getOutcomeId()).orElseThrow(() -> new NoSuchElementException("invalid.odd"));
        outcome.getOutcomeOdds().forEach(odd -> checkIfOddDatesCorrect(odd, oddRequest));
        outcome.getOutcomeOdds().add(buildOutcomeOdd(oddRequest, outcome));
    }

    private void checkIfOddDatesCorrect(OutcomeOdd odd, OddRequest oddRequest) {
        LocalDate from = LocalDate.parse(oddRequest.getFrom());
        LocalDate to = LocalDate.parse(oddRequest.getTo());
        boolean oddDateCorrect = to.isAfter(odd.getFrom()) && from.isBefore(odd.getTo()) && !from.isAfter(LocalDate.now()) && to.isBefore(odd.getOutcome().getBet().getSportEvent().getStartDate());
        if(!oddDateCorrect) {
            throw new IllegalArgumentException("invalid.odd.time.interval");
        }
    }

    private OutcomeOdd buildOutcomeOdd(OddRequest oddRequest, Outcome outcome) {
        return OutcomeOdd.builder()
                .odd(oddRequest.getOdd())
                .from(LocalDate.parse(oddRequest.getFrom()))
                .to(LocalDate.parse(oddRequest.getTo()))
                .outcome(outcome)
                .build();
    }
}
