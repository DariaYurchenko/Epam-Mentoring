package com.epam.exercises.sportbetting.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ValidationErrorResponse {
    private List<Violation> violations;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class Violation {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String fieldName;
        private String message;

        public Violation(String message) {
            this.message = message;
        }
    }

}
