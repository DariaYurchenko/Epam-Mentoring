package com.epam.exercises.sportbetting.domain.user;

import com.epam.exercises.sportbetting.domain.usergroup.UserGroup;

import java.util.Set;

public class Admin extends User {
    private Set<UserGroup> groups;

    private Admin() {
    }

    public static AdminBuilder newInstance() {
        return new AdminBuilder();
    }

    public Set<UserGroup> getGroups() {
        return groups;
    }

    public static class AdminBuilder extends UserBuilder<Admin> {
        private Set<UserGroup> groups;

        private AdminBuilder() {
        }

        public AdminBuilder withGroups(Set<UserGroup> groups) {
            this.groups = groups;
            return this;
        }

        public Admin build() {
            Admin admin = new Admin();
            admin.groups = this.groups;
            admin.email = this.email;
            admin.enabled = this.enabled;
            admin.password = this.password;
            return admin;
        }
    }

}
