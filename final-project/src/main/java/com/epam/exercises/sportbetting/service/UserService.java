package com.epam.exercises.sportbetting.service;

import com.epam.exercises.sportbetting.domain.user.User;
import com.epam.exercises.sportbetting.request.EmailConfirmRequest;
import com.epam.exercises.sportbetting.request.LogOutRequest;
import com.epam.exercises.sportbetting.request.RegistrationRequest;
import com.epam.exercises.sportbetting.response.HomeResponse;
import com.epam.exercises.sportbetting.response.LoginResponse;
import com.epam.exercises.sportbetting.response.RegistrationResponse;
import com.epam.exercises.sportbetting.security.PrincipalUser;

public interface UserService {

    LoginResponse loginUser(PrincipalUser user);

    void logOut(LogOutRequest request);

    User getUserByEmail(String email);

    RegistrationResponse registerPlayer(RegistrationRequest request);

    void confirmUserEmail(EmailConfirmRequest request);

    HomeResponse getUserInfo(PrincipalUser principalUser);

}
