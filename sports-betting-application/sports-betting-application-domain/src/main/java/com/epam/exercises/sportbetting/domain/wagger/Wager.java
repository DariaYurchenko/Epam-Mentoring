package com.epam.exercises.sportbetting.domain.wagger;

import com.epam.exercises.sportbetting.domain.user.Player;
import com.epam.exercises.sportbetting.domain.outcome.OutcomeOdd;

import java.time.LocalDateTime;
import java.util.Objects;

public class Wager {
    private OutcomeOdd outcomeOdd;
    private Player player;
    private Double amount;
    private Double wonMoney;
    private String currency;
    private LocalDateTime timeStamp;
    private boolean processed;
    private boolean win;

    private Wager() {

    }

    public static Builder newInstance() {
        return new Builder();
    }

    public OutcomeOdd getOutcomeOdd() {
        return outcomeOdd;
    }

    public Player getPlayer() {
        return player;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public boolean isProcessed() {
        return processed;
    }

    public boolean isWin() {
        return win;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public Double getWonMoney() {
        return wonMoney;
    }

    public void setWonMoney(Double wonMoney) {
        this.wonMoney = wonMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Wager wager = (Wager) o;
        return processed == wager.processed &&
            win == wager.win &&
            Objects.equals(outcomeOdd, wager.outcomeOdd) &&
            Objects.equals(player, wager.player) &&
            Objects.equals(amount, wager.amount) &&
            Objects.equals(wonMoney, wager.wonMoney) &&
            Objects.equals(currency, wager.currency) &&
            Objects.equals(timeStamp, wager.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, amount, wonMoney, currency, timeStamp, processed, win, outcomeOdd);
    }

    public static class Builder {
        private OutcomeOdd outcomeOdd;
        private Player player;
        private Double amount;
        private Double wonMoney;
        private String currency;
        private LocalDateTime timeStamp;
        private boolean processed;
        private boolean win;

        private Builder() {

        }

        public Builder withOutcomeOdd(OutcomeOdd outcomeOdd) {
            this.outcomeOdd = outcomeOdd;
            return this;
        }

        public Builder withPlayer(Player player) {
            this.player = player;
            return this;
        }

        public Builder withAmount(Double amount) {
            this.amount = amount;
            return this;
        }

        public Builder withWonMoney(Double wonMoney) {
            this.wonMoney = wonMoney;
            return this;
        }

        public Builder withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder withTimeStamp(LocalDateTime timestamp) {
            this.timeStamp = timestamp;
            return this;
        }

        public Builder withProcessed(boolean processed) {
            this.processed = processed;
            return this;
        }

        public Builder withWin(boolean win) {
            this.win = win;
            return this;
        }

        public Wager build() {
            Wager wager = new Wager();
            wager.amount = this.amount;
            wager.wonMoney = this.wonMoney;
            wager.currency = this.currency;
            wager.outcomeOdd = this.outcomeOdd;
            wager.player = this.player;
            wager.processed = this.processed;
            wager.timeStamp = this.timeStamp;
            wager.win = this.win;
            return wager;
        }
    }

}
