package com.epam.exercises.sportbetting.security;

import com.epam.exercises.sportbetting.domain.user.User;
import com.epam.exercises.sportbetting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email);

        if(user == null || !user.isEnabled()) {
            throw new UsernameNotFoundException("There is no such user.");
        }
        return new PrincipalUser(user);
    }

}
