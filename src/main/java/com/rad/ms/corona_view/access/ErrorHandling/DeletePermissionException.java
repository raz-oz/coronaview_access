package com.rad.ms.corona_view.access.ErrorHandling;

public class DeletePermissionException extends RuntimeException {
    public DeletePermissionException(String userId) {
        super("User " + userId +" can't be Deleted");
    }
}