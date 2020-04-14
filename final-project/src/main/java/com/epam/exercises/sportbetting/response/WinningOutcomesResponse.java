package com.epam.exercises.sportbetting.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WinningOutcomesResponse {
    private List<SportEventResult> results;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SportEventResult {
        private String sportEventTitle;
        private List<WinningOutcomes> winningOutcomes;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class WinningOutcomes {
            private String value;
            private String betType;
        }


    }

}
