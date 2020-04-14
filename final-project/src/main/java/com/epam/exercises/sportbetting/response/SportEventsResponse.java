package com.epam.exercises.sportbetting.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SportEventsResponse {
    private Integer sportEventId;
    private String eventType;
    private String eventTitle;
    private String startDate;
    private String endDate;
}
