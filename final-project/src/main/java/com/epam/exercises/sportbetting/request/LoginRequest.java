package com.epam.exercises.sportbetting.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @Email(message = "invalid.email", regexp = "^(.+)@(.+)$")
    @NotNull(message = "email.not.null")
    private String email;

    //@Pattern(regexp = "\\d+", message = "Password is invalid!")
    @NotNull(message = "password.not.null")
    private String password;

}
