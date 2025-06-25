package com.olisa_td.accountservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AccountRequest {

    @NotNull(message = "Account type is required.")
    @Positive(message = "Invalid account type. Allowed values are  SAVINGS, CHECKING, BUSINESS, STUDENT")
    private String accountType;

    public AccountRequest() {
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
