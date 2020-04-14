package com.epam.exercises.sportbetting.repo;

import com.epam.exercises.sportbetting.domain.token.EmailConfirmToken;
import com.epam.exercises.sportbetting.domain.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailConfirmTokenRepo extends CrudRepository<EmailConfirmToken, Integer> {

    EmailConfirmToken findEmailConfirmTokenByUser(User user);
}
