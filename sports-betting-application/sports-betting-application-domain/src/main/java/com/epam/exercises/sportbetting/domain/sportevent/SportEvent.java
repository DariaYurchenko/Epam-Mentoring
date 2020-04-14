package com.epam.exercises.sportbetting.domain.sportevent;

import com.epam.exercises.sportbetting.domain.bet.Bet;
import com.epam.exercises.sportbetting.domain.outcome.Outcome;

import java.time.LocalDateTime;
import java.util.List;

public abstract class SportEvent {
    protected String title;
    protected LocalDateTime startDate;
    protected LocalDateTime endDate;
    protected List<Bet> bets;
    protected List<Outcome> winningOutcomes;

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public List<Outcome> getWinningOutcomes() {
        return winningOutcomes;
    }

    public void setWinningOutcomes(List<Outcome> winningOutcomes) {
        this.winningOutcomes = winningOutcomes;
    }

    public abstract static class SportEventBuilder<T extends SportEvent> {
        protected String title;
        protected LocalDateTime startDate;
        protected LocalDateTime endDate;
        protected List<Bet> bets;
        protected List<Outcome> winningOutcomes;

        public SportEventBuilder<T> withTitle(String title) {
            this.title = title;
            return this;
        }

        public SportEventBuilder<T> withBets(List<Bet> bets) {
            this.bets = bets;
            return this;
        }

        public SportEventBuilder<T> withStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public SportEventBuilder<T> withEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public SportEventBuilder<T> withWinningOutcomes(List<Outcome> winningOutcomes) {
            this.winningOutcomes = winningOutcomes;
            return this;
        }

        protected abstract T construct();

        public T build() {
            T event = construct();
            event.bets = this.bets;
            event.endDate = this.endDate;
            event.startDate = this.startDate;
            event.title = this.title;
            event.winningOutcomes = this.winningOutcomes;
            return event;
        }

    }


}
