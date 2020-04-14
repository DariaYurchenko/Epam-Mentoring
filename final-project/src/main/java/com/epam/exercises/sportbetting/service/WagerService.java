package com.epam.exercises.sportbetting.service;

import com.epam.exercises.sportbetting.request.WagerRequest;
import com.epam.exercises.sportbetting.response.WagerResponse;
import com.epam.exercises.sportbetting.security.PrincipalUser;

import java.util.List;

public interface WagerService {

    List<WagerResponse> getPlayerWagers(PrincipalUser principalUser);

    void deleteWagerById(Integer id);

    void saveWager(WagerRequest wagerRequest);

}
