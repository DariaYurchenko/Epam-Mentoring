package com.epam.exercises.sportbetting.ui;

import com.epam.exercises.sportbetting.domain.user.Player;
import com.epam.exercises.sportbetting.utils.PlayerDataValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

public class PlayerUi {

    public Player createAndWelcomePlayer() throws IOException {
        Player player = createPlayer();
        welcomePlayer(player);
        return player;
    }

    private Player createPlayer() throws IOException {
        String name = askPlayerForData("Hi, what is your name?",
            "The name you've input has incorrect format.", PlayerDataValidator.NAME_REGEX);
        String accountNumber = askPlayerForData("What is your account number?",
            "You have input incorrect account number: the correct format is XXXXXXX-XXXXXXX.",
            PlayerDataValidator.ACCOUNT_NUMBER_REGEX);
        String dateOfBirth = askPlayerForData("When were you born? eg.: 1990-02-03.",
            "You have input incorrect date of birth: the correct format is XXXX-XX-XX.",
            PlayerDataValidator.DATE_OF_BIRTH_REGEX);
        String balance = askPlayerForData("How much money do you have (more than 0)?",
            "You have input incorrect balance.", PlayerDataValidator.BALANCE_REGEX);
        String currency = askPlayerForData("What is your currency? (UAH, EUR or USD)",
            "You have input incorrect currency.", PlayerDataValidator.CURRENCY_REGEX);
        return Player.newInstance()
            .withName(name)
            .withAccountNumber(accountNumber)
            .withDateOfBirth(LocalDate.parse(dateOfBirth))
            .withBalance(Double.parseDouble(balance))
            .withCurrency(currency)
            .build();
    }

    private String askPlayerForData(String question, String message, String regex) throws IOException {
        while (true) {
            try {
                System.out.println(question);
                String input = new BufferedReader(new InputStreamReader(System.in)).readLine();
                PlayerDataValidator.validateInput(input, message, regex);
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void welcomePlayer(Player player) {
        System.out.println("Welcome, " + player.getName() + "!");
        System.out.println("Your balance is " + player.getBalance() + " " + player.getCurrency() + ".");
    }

}
