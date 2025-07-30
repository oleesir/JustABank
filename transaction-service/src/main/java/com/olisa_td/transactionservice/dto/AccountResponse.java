package com.olisa_td.transactionservice.dto;

import java.math.BigDecimal;

public class AccountResponse {
    private String id;
    private String accountNumber;
    private String userId;
    private BigDecimal balance;
    private String accountType;
    private String accountStatus;

    public AccountResponse() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }


    @Override
    public String toString() {
        return "AccountResponse{" +
                "id='" + id + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", userId='" + userId + '\'' +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                ", accountStatus='" + accountStatus + '\'' +
                '}';
    }
}
