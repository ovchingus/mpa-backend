package com.itmo.mpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleDefaultException(Exception exception, WebRequest request) {
        logger.error(exception.toString());

        return new ResponseEntity<>(request.getContextPath() + ". Exception: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}


