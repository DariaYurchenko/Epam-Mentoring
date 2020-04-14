package com.epam.exercises.sportbetting.service.impl;

import com.epam.exercises.sportbetting.domain.token.EmailConfirmToken;
import com.epam.exercises.sportbetting.domain.user.Player;
import com.epam.exercises.sportbetting.domain.user.Role;
import com.epam.exercises.sportbetting.domain.user.User;
import com.epam.exercises.sportbetting.exceptions.UserExistsException;
import com.epam.exercises.sportbetting.generator.impl.RegistrationTokenGenerator;
import com.epam.exercises.sportbetting.repo.EmailConfirmTokenRepo;
import com.epam.exercises.sportbetting.repo.UserRepo;
import com.epam.exercises.sportbetting.request.EmailConfirmRequest;
import com.epam.exercises.sportbetting.request.LogOutRequest;
import com.epam.exercises.sportbetting.request.RegistrationRequest;
import com.epam.exercises.sportbetting.response.HomeResponse;
import com.epam.exercises.sportbetting.response.LoginResponse;
import com.epam.exercises.sportbetting.response.RegistrationResponse;
import com.epam.exercises.sportbetting.security.PrincipalUser;
import com.epam.exercises.sportbetting.security.jwt.JwtSecurityService;
import com.epam.exercises.sportbetting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

@Service
public class UserServiceImpl implements UserService {
    private static final int NUMBER_OF_CHARS_IN_ACCOUNT_NUMBER = 14;

    private UserRepo userRepo;
    private EmailConfirmTokenRepo tokenRepo;
    private PasswordEncoder passwordEncoder;
    private RegistrationTokenGenerator<EmailConfirmToken> generator;
    private JwtSecurityService jwtSecurityService;
    private MailSenderService mailSenderService;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, EmailConfirmTokenRepo tokenRepo, PasswordEncoder passwordEncoder, RegistrationTokenGenerator<EmailConfirmToken> generator, JwtSecurityService jwtSecurityService, MailSenderService mailSenderService) {
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
        this.passwordEncoder = passwordEncoder;
        this.generator = generator;
        this.jwtSecurityService = jwtSecurityService;
        this.mailSenderService = mailSenderService;
    }

    @Override
    public LoginResponse loginUser(PrincipalUser principalUser) {
        String jwt = jwtSecurityService.generateJwt(principalUser.getUser());
        return new LoginResponse(jwt, principalUser.getRole());
    }

    @Override
    public void logOut(LogOutRequest request) {
        jwtSecurityService.addJwtToBlackList(request.getJwt());
    }

    @Transactional
    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Transactional
    @Override
    public RegistrationResponse registerPlayer(RegistrationRequest request) {
        Player existingPlayer = (Player) userRepo.findByEmail(request.getEmail());

        if(existingPlayer != null) {
            throw new UserExistsException();
        }

        Player player = buildPlayer(request);
        userRepo.save(player);
        player.setAccountNumber(createAccountNumber(player));

        String token = generator.generate();
        tokenRepo.save(new EmailConfirmToken(token, player));

        mailSenderService.sendEmail(player.getEmail(), token);
        return buildRegistrationResponse(player);
    }

    @Transactional
    @Override
    public void confirmUserEmail(EmailConfirmRequest request) {
        User user = userRepo.findByEmail(request.getEmail());
        EmailConfirmToken token = tokenRepo.findEmailConfirmTokenByUser(user);

        generator.validate(request.getToken(), token);

        user.setEnabled(true);
    }

    @Override
    public HomeResponse getUserInfo(PrincipalUser principalUser) {
        Player player = (Player) principalUser.getUser();
        return buildHomeResponse(player);
    }

    private Player buildPlayer(RegistrationRequest request) {
        return Player.builder()
                .dateOfBirth(LocalDate.parse(request.getDateOfBirth()))
                .role(Role.ROLE_PLAYER)
                .enabled(false)
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .name(request.getName())
                .balance(Double.parseDouble(request.getBalance()))
                .currency(request.getCurrency())
                .build();
    }

    private HomeResponse buildHomeResponse(Player player) {
        return HomeResponse.builder()
                .accountNumber(player.getAccountNumber())
                .balance(player.getBalance())
                .currency(player.getCurrency())
                .email(player.getEmail())
                .name(player.getName())
                .dateOfBirth(player.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .build();
    }

    private RegistrationResponse buildRegistrationResponse(Player player) {
        return RegistrationResponse.builder()
                .email(player.getEmail())
                .name(player.getName())
                .accountNumber(player.getAccountNumber())
                .build();
    }

    private String createAccountNumber(Player player) {
        StringBuilder builder = new StringBuilder();

        Integer playerId = userRepo.findByEmail(player.getEmail()).getId();

        char[] idChars = String.valueOf(playerId).toCharArray();
        IntStream.range(0, NUMBER_OF_CHARS_IN_ACCOUNT_NUMBER - idChars.length).forEach(i -> builder.append(0));

        return builder.append(playerId)
                .insert(7, "-")
                .toString();
    }

}
