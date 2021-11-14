package com.rad.ms.corona_view.access.ErrorHandling;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String roleId) {
        super("Could not find role " + roleId);
    }
}
