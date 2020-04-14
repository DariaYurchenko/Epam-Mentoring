package com.epam.exercises.sportbetting.response.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GeneralErrorResponse {
    private String message;
    private String timeStamp;
    private int status;
}
