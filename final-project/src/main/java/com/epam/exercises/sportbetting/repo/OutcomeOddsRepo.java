package com.epam.exercises.sportbetting.repo;

import com.epam.exercises.sportbetting.domain.outcome.OutcomeOdd;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutcomeOddsRepo extends CrudRepository<OutcomeOdd, Integer> {
}
