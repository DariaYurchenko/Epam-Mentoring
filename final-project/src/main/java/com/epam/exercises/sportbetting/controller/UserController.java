package com.epam.exercises.sportbetting.controller;

import com.epam.exercises.sportbetting.request.EmailConfirmRequest;
import com.epam.exercises.sportbetting.request.LogOutRequest;
import com.epam.exercises.sportbetting.request.LoginRequest;
import com.epam.exercises.sportbetting.request.RegistrationRequest;
import com.epam.exercises.sportbetting.response.HomeResponse;
import com.epam.exercises.sportbetting.response.LoginResponse;
import com.epam.exercises.sportbetting.response.RegistrationResponse;
import com.epam.exercises.sportbetting.security.PrincipalUser;
import com.epam.exercises.sportbetting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    private AuthenticationManager authenticationManager;
    private UserService userService;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Secured("ROLE_PLAYER")
    @GetMapping(value = "/users/home")
    public ResponseEntity<HomeResponse> goHome() {
        PrincipalUser user = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userService.getUserInfo(user));
    }

    @PostMapping(value = "/users/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
        return ResponseEntity.ok(userService.loginUser(principalUser));
    }

    @PostMapping(value = "/users/log-out")
    @ResponseStatus(value = HttpStatus.OK)
    public void logout(@Valid @RequestBody LogOutRequest request) {
        userService.logOut(request);
    }

    @PostMapping(value = "/users/register")
    public ResponseEntity<RegistrationResponse> registerPlayer(@Valid @RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(userService.registerPlayer(request));
    }

    @PostMapping(value = "/users/confirm")
    @ResponseStatus(value = HttpStatus.OK)
    public void confirmEmail(@Valid @RequestBody EmailConfirmRequest request) {
        userService.confirmUserEmail(request);
    }
}
