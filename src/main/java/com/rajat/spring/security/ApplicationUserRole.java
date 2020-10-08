package com.rajat.spring.security;
import java.util.Set;

public enum ApplicationUserRole {

    STUDENT,
    ADMIN;

    private Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }
}
