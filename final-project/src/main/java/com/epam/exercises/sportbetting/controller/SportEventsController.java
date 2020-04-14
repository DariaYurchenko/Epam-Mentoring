package com.epam.exercises.sportbetting.controller;

import com.epam.exercises.sportbetting.response.BetResponse;
import com.epam.exercises.sportbetting.response.SportEventsResponse;
import com.epam.exercises.sportbetting.service.SportEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SportEventsController {
    private SportEventService sportEventService;

    @Autowired
    public SportEventsController(SportEventService sportEventService) {
        this.sportEventService = sportEventService;
    }

    @GetMapping(value = "/sport-events")
    public ResponseEntity<List<SportEventsResponse>> getSportEvents() {
        return ResponseEntity.ok(sportEventService.getSportEvents());
    }

    @GetMapping(value = "/sport-events/{id}/bets")
    public ResponseEntity<BetResponse> getBets(@PathVariable Integer id) {
        return ResponseEntity.ok(sportEventService.getBets(id));
    }
}
