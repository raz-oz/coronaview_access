package com.rad.ms.corona_view.access.ErrorHandling;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("Could not find user " + userId);
    }
}
