package com.olisa_td.transactionservice.dto;


import java.text.SimpleDateFormat;

public class TrxEmailDTO {
    String userId;
    String senderFirstName ;
    String senderLastName ;
    String accountNumber ;
    String amount ;
    String recipientFirstName ;
    String recipientLastName ;
    String recipientAccountNumber ;
    String balance ;
    String description ;
    String transactionPurpose ;
    String transactionType ;
    String timeStamp ;
    String refCode ;
    String senderEmail ;
    String recipientEmail ;

    public TrxEmailDTO() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSenderFirstName() {
        return senderFirstName;
    }

    public void setSenderFirstName(String senderFirstName) {
        this.senderFirstName = senderFirstName;
    }

    public String getSenderLastName() {
        return senderLastName;
    }

    public void setSenderLastName(String senderLastName) {
        this.senderLastName = senderLastName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRecipientFirstName() {
        return recipientFirstName;
    }

    public void setRecipientFirstName(String recipientFirstName) {
        this.recipientFirstName = recipientFirstName;
    }

    public String getRecipientLastName() {
        return recipientLastName;
    }

    public void setRecipientLastName(String recipientLastName) {
        this.recipientLastName = recipientLastName;
    }

    public String getRecipientAccountNumber() {
        return recipientAccountNumber;
    }

    public void setRecipientAccountNumber(String recipientAccountNumber) {
        this.recipientAccountNumber = recipientAccountNumber;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionPurpose() {
        return transactionPurpose;
    }

    public void setTransactionPurpose(String transactionPurpose) {
        this.transactionPurpose = transactionPurpose;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timestamp) {
        this.timeStamp = timeStamp;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }


    @Override
    public String toString() {
        String timeStamp = new SimpleDateFormat("MMM dd, yyyy h:mm a").format(this.timeStamp);

        return "TrxEmailDTO{" +
                "userId='" + userId + '\'' +
                ", senderFirstName='" + senderFirstName + '\'' +
                ", senderLastName='" + senderLastName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", amount='" + amount + '\'' +
                ", recipientFirstName='" + recipientFirstName + '\'' +
                ", recipientLastName='" + recipientLastName + '\'' +
                ", recipientAccountNumber=" + recipientAccountNumber +
                ", balance='" + balance + '\'' +
                ", description='" + description + '\'' +
                ", transactionPurpose='" + transactionPurpose + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", refCode='" + refCode + '\'' +
                ", senderEmail='" + senderEmail + '\'' +
                ", recipientEmail='" + recipientEmail + '\'' +
                '}';
    }
}
