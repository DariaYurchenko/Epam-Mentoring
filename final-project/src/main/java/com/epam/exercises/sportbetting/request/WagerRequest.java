package com.epam.exercises.sportbetting.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WagerRequest {
    @NotNull(message = "id.null")
    private Integer outcomeId;

    @NotNull(message = "wager.amount.not.null")
    @Min(message = "wager.money.small.amount", value = 100)
    private Double amount;
}
