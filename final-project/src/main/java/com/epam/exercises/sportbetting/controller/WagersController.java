package com.epam.exercises.sportbetting.controller;

import com.epam.exercises.sportbetting.request.WagerRequest;
import com.epam.exercises.sportbetting.response.WagerResponse;
import com.epam.exercises.sportbetting.security.PrincipalUser;
import com.epam.exercises.sportbetting.service.WagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Secured("ROLE_PLAYER")
public class WagersController {
    private WagerService wagerService;

    @Autowired
    public WagersController(WagerService wagerService) {
        this.wagerService = wagerService;
    }

    @GetMapping(value = "/wagers")
    public ResponseEntity<List<WagerResponse>> showPlayerWagers() {
        PrincipalUser principalUser = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(wagerService.getPlayerWagers(principalUser));
    }

    @CrossOrigin
    @DeleteMapping(value = "/wagers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteWager(@PathVariable Integer id) {
        wagerService.deleteWagerById(id);
    }

    @PostMapping(value = "/wagers")
    @ResponseStatus(HttpStatus.OK)
    public void addWager(@Valid @RequestBody WagerRequest request) {
        wagerService.saveWager(request);
    }


}
