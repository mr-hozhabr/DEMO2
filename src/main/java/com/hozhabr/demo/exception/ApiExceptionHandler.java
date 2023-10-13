package com.hozhabr.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Object> handelApiRequestException(ApiRequestException e){
        HttpStatus badRequest=HttpStatus.BAD_REQUEST;
        ApiException apiException=new ApiException(e.getMessage(), e,badRequest, ZonedDateTime.now());
        return new ResponseEntity<>(apiException,badRequest);
    }
}
