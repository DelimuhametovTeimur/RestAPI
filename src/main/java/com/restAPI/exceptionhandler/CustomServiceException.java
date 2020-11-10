package com.restAPI.exceptionhandler;

import org.springframework.http.HttpStatus;

public class CustomServiceException extends Exception {

    private final HttpStatus responseCode;

    public CustomServiceException(String message, HttpStatus code) {
        super(message);
        this.responseCode = code;
    }

    public HttpStatus getResponseCode() {
        return responseCode;
    }
}
