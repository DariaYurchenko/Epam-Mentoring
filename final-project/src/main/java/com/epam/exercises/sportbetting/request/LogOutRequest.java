package com.epam.exercises.sportbetting.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LogOutRequest {

    @NotNull(message = "invalid.token")
    private String jwt;
}
