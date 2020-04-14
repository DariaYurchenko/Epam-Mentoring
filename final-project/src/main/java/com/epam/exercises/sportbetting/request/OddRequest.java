package com.epam.exercises.sportbetting.request;

import com.epam.exercises.sportbetting.constraints.annos.OddRequestDatesAnnotation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@OddRequestDatesAnnotation
public class OddRequest {

    @NotNull(message = "id.null")
    private Integer outcomeId;

    @NotNull(message = "odd.not.null")
    @Min(value = 1, message = "minimum.odd")
    private Double odd;

    private String from;
    private String to;
}
