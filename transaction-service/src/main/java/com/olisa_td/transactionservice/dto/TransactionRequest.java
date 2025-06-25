package com.olisa_td.transactionservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TransactionRequest {

    private String id;
    private BigDecimal amount;

    @NotNull(message = "Transaction type is required.")
    @Positive(message = "Invalid transaction type. Allowed values are DEPOSIT, WITHDRAW")
    private String transactionType;

    private String description;


    public TransactionRequest(String id, BigDecimal amount, String description, String transactionType) {
        this.id = id;
        this.amount = amount;
        this.transactionType = transactionType;
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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
