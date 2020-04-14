package com.epam.exercises.sportbetting.security.jwt;

import com.epam.exercises.sportbetting.domain.user.User;

public interface JwtSecurityService {

    String generateJwt(User user);

    String getUserLoginFromJwt(String jwt);

    boolean validateJwt(String login, String jwt);

    void addJwtToBlackList(String jwt);

    boolean isJwtInBlackList(String jwt);
}
