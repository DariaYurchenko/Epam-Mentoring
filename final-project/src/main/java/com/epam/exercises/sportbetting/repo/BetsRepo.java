package com.epam.exercises.sportbetting.repo;

import com.epam.exercises.sportbetting.domain.bet.Bet;
import com.epam.exercises.sportbetting.domain.sportevent.SportEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetsRepo extends CrudRepository<Bet, Integer> {

    List<Bet> findAllBySportEvent(SportEvent sportEvent);
}
