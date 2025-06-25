package com.olisa_td.transactionservice.exception.domain;

public class InvalidTransactionException extends RuntimeException {

    private static final long serialVersionUID = 1L;


    public InvalidTransactionException(String message) {super(message);}
}
