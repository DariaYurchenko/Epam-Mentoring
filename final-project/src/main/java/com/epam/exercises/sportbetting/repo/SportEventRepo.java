package com.epam.exercises.sportbetting.repo;

import com.epam.exercises.sportbetting.domain.sportevent.SportEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportEventRepo extends CrudRepository<SportEvent, Integer> {
}
