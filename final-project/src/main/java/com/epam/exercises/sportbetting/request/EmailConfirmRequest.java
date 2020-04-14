package com.epam.exercises.sportbetting.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailConfirmRequest {
    @NotNull(message = "invalid.token")
    private String token;

    @NotNull(message = "email.not.null")
    @Email(message = "invalid.email")
    private String email;
}
