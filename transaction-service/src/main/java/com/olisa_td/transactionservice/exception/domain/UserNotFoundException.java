package com.olisa_td.transactionservice.exception.domain;

public class UserNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String message) {
        super(message);
    }
}
