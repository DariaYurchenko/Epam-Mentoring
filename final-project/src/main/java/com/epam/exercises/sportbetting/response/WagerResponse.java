package com.epam.exercises.sportbetting.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WagerResponse {
    private Integer wagerId;
    private Double amount;
    private String currency;
    private Boolean processed;
    private Double odd;
    private String betType;
    private String outcomeValue;
    private String eventType;
    private String eventTitle;
    private String startDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean win;
}
