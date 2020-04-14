package com.epam.exercises.sportbetting.service;

import com.epam.exercises.sportbetting.request.OddRequest;
import com.epam.exercises.sportbetting.request.SportEventRequest;
import com.epam.exercises.sportbetting.response.BetResponse;
import com.epam.exercises.sportbetting.response.SportEventsResponse;

import java.util.List;

public interface SportEventService {

    List<SportEventsResponse> getSportEvents();

    BetResponse getBets(Integer id);

    void saveSportEvent(SportEventRequest sportEventRequest);

    void saveOutcomeOdd(OddRequest oddRequest);


}
