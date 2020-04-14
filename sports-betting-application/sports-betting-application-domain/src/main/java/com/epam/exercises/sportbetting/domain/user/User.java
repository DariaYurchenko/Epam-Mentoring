package com.epam.exercises.sportbetting.domain.user;

import java.util.Objects;

public abstract class User {
    protected String email;
    protected String password;
    protected boolean enabled;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return enabled == user.enabled &&
            Objects.equals(email, user.email) &&
            Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, enabled);
    }

    public abstract static class UserBuilder<T extends User> {
        protected String email;
        protected String password;
        protected boolean enabled;

        public UserBuilder<T> withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder<T> withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder<T> withEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public abstract T build();

    }
}
