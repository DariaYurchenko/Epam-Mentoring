package com.epam.exercises.sportbetting.repo;

import com.epam.exercises.sportbetting.domain.user.Player;
import com.epam.exercises.sportbetting.domain.wagger.Wager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WagersRepo extends CrudRepository<Wager, Integer> {

    List<Wager> findAllByPlayer(Player player);
}
