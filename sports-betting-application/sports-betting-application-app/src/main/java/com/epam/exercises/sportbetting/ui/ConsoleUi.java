package com.epam.exercises.sportbetting.ui;

import com.epam.exercises.sportbetting.broker.Broker;
import com.epam.exercises.sportbetting.domain.outcome.OutcomeOdd;
import com.epam.exercises.sportbetting.domain.sportevent.SportEvent;
import com.epam.exercises.sportbetting.domain.user.Player;
import com.epam.exercises.sportbetting.domain.wagger.Wager;
import com.epam.exercises.sportbetting.utils.SportEventDataValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConsoleUi {
    private PlayerUi playerUi;
    private Broker broker;

    public ConsoleUi(Broker broker) {
        this.broker = broker;
        this.playerUi = new PlayerUi();
    }

    public void run() throws IOException {
        while (true) {
            Player player = playerUi.createAndWelcomePlayer();
            SportEvent sportEvent = chooseSportEvent();
            broker.stageEvent(sportEvent, betOn(sportEvent, player));
            showResults(sportEvent, player);

            System.out.println("Would you like to play again? Y");
            String answer = new BufferedReader(new InputStreamReader(System.in)).readLine().toUpperCase();
            if (!"Y".equals(answer)) {
                break;
            }
        }
    }

    private SportEvent chooseSportEvent() throws IOException {
        while (true) {
            try {
                System.out.println("Please, choose sport event.");
                broker.getSportEvents().forEach((key, value) -> System.out.println(key + ". " + value.getTitle()));
                String chosenEvent = new BufferedReader(new InputStreamReader(System.in)).readLine();
                SportEventDataValidator.validateSportEventInput(chosenEvent, broker.getSportEvents());
                return broker.getSportEvents().get(chosenEvent);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private List<Wager> betOn(SportEvent sportEvent, Player player) throws IOException {
        List<Wager> wagers = new ArrayList<>();
        List<OutcomeOdd> odds = broker.getActualOutcomeOdds(sportEvent);
        while (true) {
            try {
                String input = chooseOutcomeOdd(odds);
                if ("q".equals(input.toLowerCase())) {
                    break;
                }
                SportEventDataValidator.validateOutcomeOddsInput(input, odds);
                OutcomeOdd chosenOdd = odds.get(Integer.parseInt(input) - 1);
                broker.validateChosenOddBetType(wagers, chosenOdd);

                Double moneyBet = betOnMoney(player);
                wagers.add(createWager(player, moneyBet, chosenOdd));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return wagers;
    }

    private String chooseOutcomeOdd(List<OutcomeOdd> odds) throws IOException {
        System.out.println("Please choose an outcome to bet on! (choose a number or press q for quit)");
        odds.forEach(odd -> showOutcomeOdd(odd, odds.indexOf(odd) + 1));
        return new BufferedReader(new InputStreamReader(System.in)).readLine();
    }

    private void showOutcomeOdd(OutcomeOdd odd, int position) {
        String datePattern = "dd LLLL yyyy";
        System.out.println(String.format("%s. %s %s The odd on this is %s. Valid from %s to %s", position, odd.getOutcome().getBet().getDescription(),
            odd.getOutcome().getValue(), odd.getOdd(), odd.getFrom().format(DateTimeFormatter.ofPattern(datePattern)),
            odd.getTo().format(DateTimeFormatter.ofPattern(datePattern))));
    }

    private Double betOnMoney(Player player) throws IOException {
        while (true) {
            try {
                System.out.println("What amount do you wish to bet on it?");
                String money = new BufferedReader(new InputStreamReader(System.in)).readLine();
                SportEventDataValidator.validateMoneyBetOn(money);
                broker.betOnMoney(player, money);
                System.out.println("Your new balance is " + player.getBalance() + " " + player.getCurrency() + ".");
                return Double.parseDouble(money);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Wager createWager(Player player, Double money, OutcomeOdd chosenOdd) {
        return Wager.newInstance()
            .withAmount(money)
            .withCurrency(player.getCurrency())
            .withPlayer(player)
            .withTimeStamp(LocalDateTime.now())
            .withOutcomeOdd(chosenOdd)
            .build();
    }

    private void showResults(SportEvent sportEvent, Player player) {
        System.out.println("The winners are: ");
        sportEvent.getWinningOutcomes().forEach(outcome -> System.out.println(outcome.getValue()));
        System.out.println("Now your balance is " + player.getBalance() + " " + player.getCurrency() + ".");
    }

}
