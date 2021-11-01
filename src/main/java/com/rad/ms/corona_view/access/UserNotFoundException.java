package com.rad.ms.corona_view.access;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("Could not found user " + userId);
    }
}
