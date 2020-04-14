package com.epam.exercises.sportbetting.domain.sportevent;

public class FootballSportEvent extends SportEvent {

    public static FootballSportEventBuilder newInstance() {
        return new FootballSportEventBuilder();
    }

    private FootballSportEvent() {
    }

    public static class FootballSportEventBuilder extends SportEventBuilder<FootballSportEvent> {

        private FootballSportEventBuilder() {
        }

        @Override
        public FootballSportEvent construct() {
            return new FootballSportEvent();
        }
    }
}
