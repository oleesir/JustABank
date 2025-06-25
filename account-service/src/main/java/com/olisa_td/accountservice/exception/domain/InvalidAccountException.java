package com.olisa_td.accountservice.exception.domain;

public class InvalidAccountException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidAccountException(String message) {
        super(message);
    }


}
