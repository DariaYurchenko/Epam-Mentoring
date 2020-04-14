package com.epam.exercises.sportbetting.domain.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_PLAYER;

    @Override
    public String getAuthority() {
        return name();
    }
}
