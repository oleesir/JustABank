package com.olisa_td.transactionservice.dto.moveFund;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class MoveFundRequestDTO {

    @NotNull(message = "Account number is required.")
    private Long accountNumber;

    @NotNull(message = "Recipient account number is required.")
    private Long recipientAccountNumber;

    @NotNull(message = "Amount is required.")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;


    public MoveFundRequestDTO() {
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
}
