package com.olisa_td.transactionservice.exception.domain;

public class DailyLimitException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DailyLimitException(String message) {
        super(message);
    }
}
