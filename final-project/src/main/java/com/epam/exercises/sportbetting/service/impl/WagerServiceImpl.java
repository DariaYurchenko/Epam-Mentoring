package com.epam.exercises.sportbetting.service.impl;

import com.epam.exercises.sportbetting.domain.outcome.Outcome;
import com.epam.exercises.sportbetting.domain.outcome.OutcomeOdd;
import com.epam.exercises.sportbetting.domain.user.Player;
import com.epam.exercises.sportbetting.domain.wagger.Wager;
import com.epam.exercises.sportbetting.exceptions.NoActualOutcomeOddException;
import com.epam.exercises.sportbetting.exceptions.WagerProcessedException;
import com.epam.exercises.sportbetting.repo.OutcomesRepo;
import com.epam.exercises.sportbetting.repo.UserRepo;
import com.epam.exercises.sportbetting.repo.WagersRepo;
import com.epam.exercises.sportbetting.request.WagerRequest;
import com.epam.exercises.sportbetting.response.WagerResponse;
import com.epam.exercises.sportbetting.security.PrincipalUser;
import com.epam.exercises.sportbetting.service.WagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class WagerServiceImpl implements WagerService {
    private WagersRepo wagersRepo;
    private OutcomesRepo outcomesRepo;
    private UserRepo userRepo;

    @Autowired
    public WagerServiceImpl(WagersRepo wagersRepo, OutcomesRepo outcomesRepo, UserRepo userRepo) {
        this.wagersRepo = wagersRepo;
        this.outcomesRepo = outcomesRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    @Override
    public List<WagerResponse> getPlayerWagers(PrincipalUser principalUser) {
        Player player = (Player) principalUser.getUser();
        List<Wager> userWagers = wagersRepo.findAllByPlayer(player);
        return buildUserWagersResponseList(userWagers);
    }

    @Transactional
    @Override
    public void deleteWagerById(Integer id) {
        Wager wager = wagersRepo.findById(id).orElseThrow(() -> new NoSuchElementException("wager.exception"));
        if(wager.getProcessed() || checkIfEventStarted(wager)) {
            throw new WagerProcessedException();
        }
        wagersRepo.delete(wager);
    }

    @Transactional
    @Override
    public void saveWager(WagerRequest wagerRequest) {
        PrincipalUser principalUser = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Player player = (Player) principalUser.getUser();
        validateMoneyBetOn(wagerRequest, player);

        Outcome outcome = outcomesRepo.findById(wagerRequest.getOutcomeId()).orElseThrow(() -> new NoSuchElementException("outcome.exception"));
        validateChosenOutcomeBetType(player, outcome);

        player.setBalance(player.getBalance() - wagerRequest.getAmount());
        userRepo.save(player);

        Wager wager = buildWager(wagerRequest, player, outcome);
        wagersRepo.save(wager);
    }

    private void validateMoneyBetOn(WagerRequest wagerRequest, Player player) {
        if(wagerRequest.getAmount() > player.getBalance()) {
            throw new IllegalArgumentException("invalid.money.bet.on");
        }
    }

    private void validateChosenOutcomeBetType(Player player, Outcome outcome) {
        List<Wager> playerWagers = wagersRepo.findAllByPlayer(player);

        boolean containsOdWithTheSameType = playerWagers.stream()
                .map(Wager::getOutcomeOdd)
                .anyMatch(odd -> odd.getOutcome().getBet().getType() == outcome.getBet().getType());

        if (containsOdWithTheSameType) {
            throw new IllegalArgumentException("outcome.same.type");
        }
    }

    private Wager buildWager(WagerRequest wagerRequest, Player player, Outcome outcome) {
        return Wager.builder()
                .player(player)
                .amount(wagerRequest.getAmount())
                .currency(player.getCurrency())
                .processed(false)
                .timeStamp(LocalDateTime.now())
                .outcomeOdd(findActualOutcomeOdd(outcome))
                .build();
    }

    private OutcomeOdd findActualOutcomeOdd(Outcome outcome) {
        return outcome.getOutcomeOdds().stream()
                .filter(this::checkIfCurrentDateIsInInterval)
                .findFirst()
                .orElseThrow(NoActualOutcomeOddException::new);
    }

    private boolean checkIfCurrentDateIsInInterval(OutcomeOdd odd) {
        return LocalDate.now().isBefore(odd.getTo()) && LocalDate.now().isAfter(odd.getFrom());
    }

    private List<WagerResponse> buildUserWagersResponseList(List<Wager> playerWagers) {
        return playerWagers.stream()
                .map(this::buildUserWagers)
                .collect(Collectors.toList());
    }

    private WagerResponse buildUserWagers(Wager wager) {
        return WagerResponse.builder()
                .wagerId(wager.getId())
                .amount(wager.getAmount())
                .currency(wager.getCurrency())
                .processed(wager.getProcessed())
                .win(wager.getWin())
                .odd(wager.getOutcomeOdd().getOdd())
                .outcomeValue(wager.getOutcomeOdd().getOutcome().getValue())
                .betType(wager.getOutcomeOdd().getOutcome().getBet().getType().name())
                .startDate(wager.getOutcomeOdd().getOutcome().getBet().getSportEvent().getStartDate().toString())
                .eventTitle(wager.getOutcomeOdd().getOutcome().getBet().getSportEvent().getTitle())
                .eventType(wager.getOutcomeOdd().getOutcome().getBet().getSportEvent().getSportEventType().name())
                .build();
    }

    private boolean checkIfEventStarted(Wager wager) {
        return wager.getOutcomeOdd().getOutcome().getBet().getSportEvent().getStartDate().isBefore(LocalDate.now());
    }

}
