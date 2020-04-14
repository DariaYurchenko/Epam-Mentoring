package com.epam.exercises.sportbetting.builder;

import com.epam.exercises.sportbetting.domain.bet.Bet;
import com.epam.exercises.sportbetting.domain.outcome.Outcome;
import com.epam.exercises.sportbetting.domain.sportevent.SportEvent;
import com.epam.exercises.sportbetting.response.BetResponse;
import com.epam.exercises.sportbetting.response.BetResponse.BetData;
import com.epam.exercises.sportbetting.response.BetResponse.BetData.OutcomeData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BetResponseBuilder {

    public BetResponse buildBetResponse(SportEvent sportEvent) {
        return BetResponse.builder()
                    .endDate(sportEvent.getEndDate().toString())
                    .startDate(sportEvent.getStartDate().toString())
                    .eventTitle(sportEvent.getTitle())
                    .eventType(sportEvent.getSportEventType().name())
                    .betData(buildBetDataList(sportEvent)).
                    build();
    }

    private List<BetData> buildBetDataList(SportEvent sportEvent) {
        return sportEvent.getBets().stream()
                .map(this::buildBetData)
                .collect(Collectors.toList());
    }

    private BetData buildBetData(Bet bet) {
        return BetData.builder()
                .betType(bet.getType().name())
                .description(bet.getDescription())
                .outcomeData(buildOutcomeDataList(bet))
                .build();
    }

    private List<OutcomeData> buildOutcomeDataList(Bet bet) {
        return bet.getOutcomes().stream()
                .map(this::buildOutcomeData)
                .collect(Collectors.toList());
    }

    private OutcomeData buildOutcomeData(Outcome outcome) {
        return OutcomeData.builder()
                .id(outcome.getId())
                .value(outcome.getValue())
                .build();
    }
}
