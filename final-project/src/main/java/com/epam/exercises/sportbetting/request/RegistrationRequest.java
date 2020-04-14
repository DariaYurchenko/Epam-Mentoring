package com.epam.exercises.sportbetting.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    @NotNull(message = "name.not.null")
    @Pattern(regexp = "[a-zA-Z]+", message = "invalid.name")
    private String name;

    @NotNull(message = "email.not.null")
    @Email(message = "invalid.email")
    private String email;

    @NotNull(message = "password.not.null")
    private String password;

    @NotNull(message = "date.birth.not.null")
    @Pattern(regexp = "[\\d]{4}[-][\\d]{2}[-][\\d]{2}", message = "invalid.date.birth")
    private String dateOfBirth;

    @NotNull(message = "balance.not.null")
    @Pattern(regexp = "\\d+", message = "invalid.balance")
    private String balance;

    @NotNull(message = "currency.not.null")
    @Pattern(regexp = "UAH|EUR|USD", message = "invalid.currency")
    private String currency;
}
