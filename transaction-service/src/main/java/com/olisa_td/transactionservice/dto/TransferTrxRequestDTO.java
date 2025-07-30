package com.olisa_td.transactionservice.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransferTrxRequestDTO {

    @NotNull(message = "Amount is required.")
    @Min(value = 0, message = "Account number must be positive.")
    private BigDecimal amount;

    @NotNull(message = "Account number is required.")
    @Digits(integer = 10, fraction = 0, message = "Account number must be a numeric value with up to 10 digits.")
    private Long accountNumber;

    @NotNull(message = "Recipient number is required.")
    @Digits(integer = 10, fraction = 0, message = "Account number must be a numeric value with up to 10 digits.")
    private Long recipientAccountNumber;

    private String description;



    public TransferTrxRequestDTO() {

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

    public Long getRecipientAccountNumber() {
        return recipientAccountNumber;
    }

    public void setRecipientAccountNumber(Long recipientAccountNumber) {
        this.recipientAccountNumber = recipientAccountNumber;
    }


}
