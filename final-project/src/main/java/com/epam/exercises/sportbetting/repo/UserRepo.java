package com.epam.exercises.sportbetting.repo;

import com.epam.exercises.sportbetting.domain.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {

    User findByEmail(String email);

}
