package com.olisa_td.transactionservice.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransferRequest {

    private String id;

    @NotNull(message = "Amount is required.")
    private BigDecimal amount;

    @NotNull(message = "Account number is required.")
    @Digits(integer = 10, fraction = 0, message = "Account number must be a numeric value with up to 10 digits.")
    private Long accountNumber;

    private String description;


    public TransferRequest(String id, BigDecimal amount, Long accountNumber, String description) {
        this.id = id;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
