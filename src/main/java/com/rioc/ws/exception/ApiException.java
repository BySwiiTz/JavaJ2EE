package com.rioc.ws.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{
    private HttpStatus httpStatus;

    public ApiException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }
    
}
