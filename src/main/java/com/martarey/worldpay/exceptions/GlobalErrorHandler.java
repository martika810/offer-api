package com.martarey.worldpay.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.ResponseEntity.status;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity handleInvalidRequest(Exception exception){
        return status(HttpStatus.BAD_REQUEST).body("Please review your request");
    }
}
