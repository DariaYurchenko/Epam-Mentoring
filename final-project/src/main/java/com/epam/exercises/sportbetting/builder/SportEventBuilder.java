package com.epam.exercises.sportbetting.builder;

import com.epam.exercises.sportbetting.domain.bet.Bet;
import com.epam.exercises.sportbetting.domain.bet.BetType;
import com.epam.exercises.sportbetting.domain.outcome.Outcome;
import com.epam.exercises.sportbetting.domain.outcome.OutcomeOdd;
import com.epam.exercises.sportbetting.domain.sportevent.SportEvent;
import com.epam.exercises.sportbetting.domain.sportevent.SportEventType;
import com.epam.exercises.sportbetting.request.SportEventRequest;
import com.epam.exercises.sportbetting.request.SportEventRequest.BetRequest;
import com.epam.exercises.sportbetting.request.SportEventRequest.BetRequest.OutcomeRequest;
import com.epam.exercises.sportbetting.request.SportEventRequest.BetRequest.OutcomeRequest.OutcomeOddRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class SportEventBuilder {

    public SportEvent buildSportEvent(SportEventRequest request) {
        SportEvent sportEvent = SportEvent.builder()
                .endDate(LocalDate.parse(request.getEndDate()))
                .startDate(LocalDate.parse(request.getStartDate()))
                .sportEventType(SportEventType.values()[Integer.parseInt(request.getSportEventType())])
                .title(request.getTitle())
                .bets(buildBetsList(request.getBets()))
                .build();
        sportEvent.getBets().forEach(bet -> bet.setSportEvent(sportEvent));
        return sportEvent;
    }

    private List<Bet> buildBetsList(List<BetRequest> requests) {
        List<Bet> bets =  requests.stream()
                .map(this::buildBet)
                .collect(toList());
        bets.forEach(bet -> bet.getOutcomes().forEach(outcome -> outcome.setBet(bet)));
        return bets;
    }

    private Bet buildBet(BetRequest betRequest) {
        return Bet.builder()
                .description(betRequest.getDescription())
                .outcomes(buildOutcomesList(betRequest.getOutcomes()))
                .type(BetType.values()[Integer.parseInt(betRequest.getType())])
                .build();
    }

    private List<Outcome> buildOutcomesList(List<OutcomeRequest> outcomeRequests) {
        List<Outcome> outcomes = outcomeRequests.stream()
                .map(this::buildOutcome)
                .collect(toList());
        outcomes.forEach(outcome -> outcome.getOutcomeOdds().forEach(odd -> odd.setOutcome(outcome)));
        return outcomes;
    }

    private Outcome buildOutcome(OutcomeRequest outcomeRequest) {
        return Outcome.builder()
                .value(outcomeRequest.getValue())
                .outcomeOdds(buildOutcomeOdds(outcomeRequest.getOutcomeOdds()))
                .build();
    }

    private List<OutcomeOdd> buildOutcomeOdds(List<OutcomeOddRequest> oddRequests) {
        return oddRequests.stream()
                .map(this::buildOutcomeOdd)
                .collect(toList());
    }

    private OutcomeOdd buildOutcomeOdd(OutcomeOddRequest oddRequest) {
        return OutcomeOdd.builder()
                .from(LocalDate.parse(oddRequest.getFrom()))
                .to(LocalDate.parse(oddRequest.getTo()))
                .odd(oddRequest.getOdd())
                .build();
    }
}
