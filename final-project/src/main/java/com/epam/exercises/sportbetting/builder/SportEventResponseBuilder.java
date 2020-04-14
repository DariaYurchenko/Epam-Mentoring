package com.epam.exercises.sportbetting.builder;

import com.epam.exercises.sportbetting.domain.sportevent.SportEvent;
import com.epam.exercises.sportbetting.response.SportEventsResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SportEventResponseBuilder {

    public List<SportEventsResponse> buildSportEventResponseList(List<SportEvent> events) {
        return events.stream()
                .map(this::buildSportEventResponse)
                .collect(Collectors.toList());
    }

    private SportEventsResponse buildSportEventResponse(SportEvent event) {
        return SportEventsResponse.builder()
                .sportEventId(event.getId())
                .eventTitle(event.getTitle())
                .eventType(event.getSportEventType().name())
                .endDate(event.getEndDate().toString())
                .startDate(event.getStartDate().toString())
                .build();
    }
}
