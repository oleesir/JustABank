package com.olisa_td.transactionservice.exception.domain;

public class InsufficientFundsException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public InsufficientFundsException(String message) {
        super(message);
    }
}
