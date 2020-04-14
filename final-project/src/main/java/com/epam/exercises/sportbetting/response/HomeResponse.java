package com.epam.exercises.sportbetting.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeResponse {
    private String name;
    private String email;
    private String accountNumber;
    private String dateOfBirth;
    private String currency;
    private Double balance;
}
