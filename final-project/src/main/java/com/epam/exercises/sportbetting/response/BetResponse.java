package com.epam.exercises.sportbetting.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BetResponse {

    private String eventType;
    private String eventTitle;
    private String startDate;
    private String endDate;
    private List<BetData> betData;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BetData {
        private String betType;
        private String description;
        private List<OutcomeData> outcomeData;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class OutcomeData{
            private Integer id;
            private String value;
        }

    }
}
