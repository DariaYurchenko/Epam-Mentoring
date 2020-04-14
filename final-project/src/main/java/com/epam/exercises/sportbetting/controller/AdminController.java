package com.epam.exercises.sportbetting.controller;

import com.epam.exercises.sportbetting.request.OddRequest;
import com.epam.exercises.sportbetting.request.SportEventRequest;
import com.epam.exercises.sportbetting.response.WinningOutcomesResponse;
import com.epam.exercises.sportbetting.service.BrokerService;
import com.epam.exercises.sportbetting.service.SportEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Secured("ROLE_ADMIN")
public class AdminController {
    private SportEventService sportEventService;
    private BrokerService brokerService;

    @Autowired
    public AdminController(SportEventService sportEventService, BrokerService brokerService) {
        this.sportEventService = sportEventService;
        this.brokerService = brokerService;
    }

    @PostMapping(value = "/sport-events")
    @ResponseStatus(HttpStatus.OK)
    public void addNewSportEvent(@Valid @RequestBody SportEventRequest sportEventRequest) {
        sportEventService.saveSportEvent(sportEventRequest);
    }

    @PostMapping(value = "/sport-events/odds")
    @ResponseStatus(HttpStatus.OK)
    public void addNewOdd(@Valid @RequestBody OddRequest oddRequest) {
        sportEventService.saveOutcomeOdd(oddRequest);
    }

    @GetMapping(value = "/sport-events/stage")
    public ResponseEntity<WinningOutcomesResponse> getWinningOutcomes() {
        return ResponseEntity.ok(brokerService.stageEvent());
    }
}
