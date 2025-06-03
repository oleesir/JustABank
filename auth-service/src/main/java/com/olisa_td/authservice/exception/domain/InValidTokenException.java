package com.olisa_td.authservice.exception.domain;

public class InValidTokenException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public InValidTokenException(String message) {
        super(message);
    }
}
