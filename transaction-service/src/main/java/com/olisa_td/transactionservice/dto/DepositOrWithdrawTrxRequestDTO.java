package com.olisa_td.transactionservice.dto;

import com.olisa_td.transactionservice.jpa.TransactionPurpose;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class DepositOrWithdrawTrxRequestDTO {

    @NotNull(message = "Account number is required.")
    private Long accountNumber;

    @NotNull(message = "Amount is required.")
    @Min(value = 0, message = "Account number must be positive.")
    private BigDecimal amount;

    @NotNull(message = "Transaction purpose is required.")
    private TransactionPurpose transactionPurpose;

    private String description;


    public DepositOrWithdrawTrxRequestDTO() {
    }


    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionPurpose getTransactionPurpose() {
        return transactionPurpose;
    }

    public void setTransactionPurpose(TransactionPurpose transactionPurpose) {
        this.transactionPurpose = transactionPurpose;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
