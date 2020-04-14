package com.epam.exercises.sportbetting.domain.bet;

import com.epam.exercises.sportbetting.domain.sportevent.SportEvent;
import com.epam.exercises.sportbetting.domain.outcome.Outcome;

import java.util.List;
import java.util.Objects;

public class Bet {
    private String description;
    private BetType type;
    private SportEvent sportEvent;
    private List<Outcome> outcomes;

    private Bet() {
    }

    public static Builder newInstance() {
        return new Builder();
    }

    public String getDescription() {
        return description;
    }

    public BetType getType() {
        return type;
    }

    public SportEvent getSportEvent() {
        return sportEvent;
    }

    public List<Outcome> getOutcomes() {
        return outcomes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bet bet = (Bet) o;
        return Objects.equals(description, bet.description) &&
            type == bet.type &&
            Objects.equals(sportEvent, bet.sportEvent) &&
            Objects.equals(outcomes, bet.outcomes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, type, sportEvent, outcomes);
    }

    public static class Builder {
        private String description;
        private BetType type;
        private SportEvent sportEvent;
        private List<Outcome> outcomes;

        private Builder() {

        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withType(BetType type) {
            this.type = type;
            return this;
        }

        public Builder withSportEvent(SportEvent sportEvent) {
            this.sportEvent = sportEvent;
            return this;
        }

        public Builder withOutcomes(List<Outcome> outcomes) {
            this.outcomes = outcomes;
            return this;
        }

        public Bet build() {
            Bet bet = new Bet();
            bet.type = this.type;
            bet.description = this.description;
            bet.sportEvent = this.sportEvent;
            bet.outcomes = this.outcomes;
            return bet;
        }
    }
}
