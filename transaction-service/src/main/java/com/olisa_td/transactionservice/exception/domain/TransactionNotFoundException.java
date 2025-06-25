package com.olisa_td.transactionservice.exception.domain;

public class TransactionNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TransactionNotFoundException(String message) {
        super(message);
    }
}