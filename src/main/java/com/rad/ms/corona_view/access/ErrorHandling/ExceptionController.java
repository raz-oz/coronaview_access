package com.rad.ms.corona_view.access.ErrorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundHandler(UserNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidInputHandler(InvalidInputException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(PermissionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String permissionNotFoundHandler(PermissionNotFoundException ex) {
        return ex.getMessage();
    }

}