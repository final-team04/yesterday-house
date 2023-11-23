package com.ssafy.main.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.ssafy.util.ResponseMessage.BAD_REQUEST;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }
}
