package com.rad.ms.corona_view.access.ErrorHandling;

public class PermissionException extends RuntimeException {
    public PermissionException(String msg) {
        super("User " + msg +" is unauthorized");
    }
}
