package com.epam.exercises.sportbetting.domain.sportevent;

public class TennisSportEvent extends SportEvent {

    public static TennisSportEventBuilder newInstance() {
        return new TennisSportEventBuilder();
    }

    private TennisSportEvent() {
    }

    public static class TennisSportEventBuilder extends SportEventBuilder<TennisSportEvent> {

        private TennisSportEventBuilder() {
        }

        @Override
        public TennisSportEvent construct() {
            return new TennisSportEvent();
        }
    }
}
