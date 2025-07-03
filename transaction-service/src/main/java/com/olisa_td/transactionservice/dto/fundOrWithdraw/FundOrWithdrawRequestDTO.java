package com.olisa_td.transactionservice.dto.fundOrWithdraw;

import com.olisa_td.transactionservice.jpa.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class FundOrWithdrawRequestDTO {

    private  String userId;

    @NotNull(message = "Account number is required.")
    private Long accountNumber;

    @NotNull(message = "Amount is required.")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Transaction type is required.")
    @Positive(message = "Invalid transaction type. Allowed values is FUND, WITHDRAW")
    private TransactionType transactionType;



    public FundOrWithdrawRequestDTO() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
