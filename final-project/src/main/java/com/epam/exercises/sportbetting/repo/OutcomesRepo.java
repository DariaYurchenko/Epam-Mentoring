package com.epam.exercises.sportbetting.repo;

import com.epam.exercises.sportbetting.domain.bet.Bet;
import com.epam.exercises.sportbetting.domain.outcome.Outcome;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutcomesRepo extends CrudRepository<Outcome, Integer> {

    List<Outcome> findAllByBet(Bet bet);

}
