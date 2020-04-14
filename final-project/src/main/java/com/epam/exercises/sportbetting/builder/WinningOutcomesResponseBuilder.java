package com.epam.exercises.sportbetting.builder;

import com.epam.exercises.sportbetting.domain.outcome.Outcome;
import com.epam.exercises.sportbetting.domain.sportevent.SportEvent;
import com.epam.exercises.sportbetting.response.WinningOutcomesResponse;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class WinningOutcomesResponseBuilder {

    public WinningOutcomesResponse buildWinningOutcomesResponse(List<SportEvent> sportEvents) {
        return new WinningOutcomesResponse(buildSportEventResultsList(sportEvents));
    }

    private List<WinningOutcomesResponse.SportEventResult> buildSportEventResultsList(List<SportEvent> sportEvents) {
        return sportEvents.stream()
                .map(this::buildSportEventResult)
                .collect(toList());
    }

    private WinningOutcomesResponse.SportEventResult buildSportEventResult(SportEvent sportEvent) {
        return WinningOutcomesResponse.SportEventResult.builder()
                .sportEventTitle(sportEvent.getTitle())
                .winningOutcomes(this.buildWinningOutcomesList(sportEvent.getWinningOutcomes()))
                .build();
    }

    private List<WinningOutcomesResponse.SportEventResult.WinningOutcomes> buildWinningOutcomesList(List<Outcome> outcomes) {
        return outcomes.stream()
                .map(outcome -> new WinningOutcomesResponse.SportEventResult.WinningOutcomes(outcome.getValue(), outcome.getBet().getType().name()))
                .collect(toList());
    }
}
