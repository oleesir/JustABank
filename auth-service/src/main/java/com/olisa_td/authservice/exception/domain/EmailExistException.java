package com.olisa_td.authservice.exception.domain;


public class EmailExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailExistException(String message) {
        super(message);
    }
}
