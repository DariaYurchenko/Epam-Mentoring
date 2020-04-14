package com.epam.exercises.sportbetting.domain.outcome;

import java.time.LocalDateTime;
import java.util.Objects;

public class OutcomeOdd {
    private Outcome outcome;
    private Double odd;
    private LocalDateTime from;
    private LocalDateTime to;

    private OutcomeOdd() {
    }

    public static Builder newInstance() {
        return new Builder();
    }

    public Double getOdd() {
        return odd;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    public static class Builder {
        private Outcome outcome;
        private Double odd;
        private LocalDateTime from;
        private LocalDateTime to;

        private Builder() {

        }

        public Builder withOutcome(Outcome outcome) {
            this.outcome = outcome;
            return this;
        }

        public Builder withOdd(Double odd) {
            this.odd = odd;
            return this;
        }

        public Builder withDateFrom(LocalDateTime from) {
            this.from = from;
            return this;
        }

        public Builder withDateTo(LocalDateTime to) {
            this.to = to;
            return this;
        }

        public OutcomeOdd build() {
            OutcomeOdd odd = new OutcomeOdd();
            odd.outcome = this.outcome;
            odd.from = this.from;
            odd.to = this.to;
            odd.odd = this.odd;
            return odd;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OutcomeOdd odd1 = (OutcomeOdd) o;
        return Objects.equals(odd, odd1.odd) &&
            Objects.equals(outcome.getValue(), odd1.outcome.getValue()) &&
            Objects.equals(outcome.getBet().getSportEvent(), odd1.outcome.getBet().getSportEvent()) &&
            Objects.equals(outcome.getBet().getDescription(), odd1.outcome.getBet().getDescription()) &&
            outcome.getBet().getType() == odd1.getOutcome().getBet().getType() &&
            Objects.equals(from, odd1.from) &&
            Objects.equals(to, odd1.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(odd, from, to, outcome.getValue(), outcome.getBet().getSportEvent(), outcome.getBet().getDescription(), outcome.getBet().getType());
    }
}
