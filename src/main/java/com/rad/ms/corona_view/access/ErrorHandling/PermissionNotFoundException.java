package com.rad.ms.corona_view.access.ErrorHandling;

public class PermissionNotFoundException extends RuntimeException {
    public PermissionNotFoundException(String id){
        super("Couldn't find the \""+id+"\" permission.");
    }
}
