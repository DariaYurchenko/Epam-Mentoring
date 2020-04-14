package com.epam.exercises.sportbetting.generator.impl;

import com.epam.exercises.sportbetting.domain.token.EmailConfirmToken;
import com.epam.exercises.sportbetting.exceptions.InvalidTokenException;
import com.epam.exercises.sportbetting.exceptions.TokenExpiredException;
import com.epam.exercises.sportbetting.generator.Generator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Component
public class RegistrationTokenGenerator<T extends EmailConfirmToken> implements Generator<T> {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void validate(String requestToken, T token) {
        if(token == null || !token.getToken().equals(requestToken)) {
            throw new InvalidTokenException();
        }
        if(token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException();
        }
    }
}
