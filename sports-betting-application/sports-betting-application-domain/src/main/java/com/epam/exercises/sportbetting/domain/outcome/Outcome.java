package com.epam.exercises.sportbetting.domain.outcome;

import com.epam.exercises.sportbetting.domain.bet.Bet;

import java.util.List;
import java.util.Objects;

public class Outcome {
    private String value;
    private Bet bet;
    private List<OutcomeOdd> outcomeOdds;

    public Outcome(String value, List<OutcomeOdd> outcomeOdds) {
        this.value = value;
        this.outcomeOdds = outcomeOdds;
    }

    public String getValue() {
        return value;
    }

    public List<OutcomeOdd> getOutcomeOdds() {
        return outcomeOdds;
    }


    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Outcome outcome = (Outcome) o;
        return Objects.equals(value, outcome.value) &&
            Objects.equals(bet.getDescription(), outcome.bet.getDescription()) &&
            Objects.equals(bet.getSportEvent(), outcome.bet.getSportEvent()) &&
            bet.getType() == outcome.bet.getType() &&
            Objects.equals(outcomeOdds, outcome.outcomeOdds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, outcomeOdds, bet.getType(), bet.getDescription(), bet.getSportEvent());
    }
}
