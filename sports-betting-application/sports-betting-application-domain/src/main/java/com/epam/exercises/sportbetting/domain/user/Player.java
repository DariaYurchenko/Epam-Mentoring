package com.epam.exercises.sportbetting.domain.user;

import java.time.LocalDate;
import java.util.Objects;

public class Player extends User {
    private String name;
    private String accountNumber;
    private Double balance;
    private String currency;
    private LocalDate dateOfBirth;

    private Player() {
    }

    public static PlayerBuilder newInstance() {
        return new PlayerBuilder();
    }

    public String getName() {
        return name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Player player = (Player) o;
        return super.equals(player) &&
            Objects.equals(name, player.name) &&
            Objects.equals(accountNumber, player.accountNumber) &&
            Objects.equals(balance, player.balance) &&
            Objects.equals(currency, player.currency) &&
            Objects.equals(dateOfBirth, player.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, accountNumber, balance, currency, dateOfBirth);
    }

    public static class PlayerBuilder extends UserBuilder<Player> {
        private String name;
        private String accountNumber;
        private Double balance;
        private String currency;
        private LocalDate dateOfBirth;

        private PlayerBuilder() {
        }

        public PlayerBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PlayerBuilder withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public PlayerBuilder withBalance(Double balance) {
            this.balance = balance;
            return this;
        }

        public PlayerBuilder withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public PlayerBuilder withDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Player build() {
            Player player = new Player();
            player.accountNumber = this.accountNumber;
            player.balance = this.balance;
            player.currency = this.currency;
            player.dateOfBirth = this.dateOfBirth;
            player.name = this.name;
            player.email = this.email;
            player.enabled = this.enabled;
            player.password = this.password;
            return player;
        }

    }

}
