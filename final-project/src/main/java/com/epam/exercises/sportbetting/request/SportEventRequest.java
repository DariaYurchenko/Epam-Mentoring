package com.epam.exercises.sportbetting.request;

import com.epam.exercises.sportbetting.constraints.annos.SportEventRequestDatesAnnotation;
import com.epam.exercises.sportbetting.constraints.annos.SportEventRequestTypeAnnotation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SportEventRequestDatesAnnotation
@SportEventRequestTypeAnnotation
public class SportEventRequest {
    @NotNull(message = "title.null")
    private String title;
    private String sportEventType;
    private String startDate;
    private String endDate;
    @Valid
    @NotNull(message = "bets.null")
    private List<BetRequest> bets;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BetRequest {
        @NotNull(message = "bet.description.null")
        private String description;
        private String type;
        @Valid
        @NotNull(message = "outcomes.null")
        private List<OutcomeRequest> outcomes;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class OutcomeRequest {
            @NotNull(message = "outcome.value.null")
            private String value;
            @Valid
            @NotNull(message = "odds.null")
            private List<OutcomeOddRequest> outcomeOdds;

            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            public static class OutcomeOddRequest {
                @NotNull(message = "odd.not.null")
                @Min(value = 1, message = "minimum.odd")
                private Double odd;

                private String from;
                private String to;
            }
        }
    }


}
