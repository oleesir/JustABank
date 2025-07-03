package com.olisa_td.transactionservice.dto.moveFund;

import com.olisa_td.transactionservice.jpa.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class MoveFundResponseDTO {

    private Long accountNumber;

    private Long recipientAccountNumber;

    private BigDecimal amount;

    private TransactionType transactionType;



    public MoveFundResponseDTO() {
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getRecipientAccountNumber() {
        return recipientAccountNumber;
    }

    public void setRecipientAccountNumber(Long recipientAccountNumber) {
        this.recipientAccountNumber = recipientAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
