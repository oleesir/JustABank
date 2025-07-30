package com.olisa_td.transactionservice.dto;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DepositOrWithdrawEmailDTO {

    String senderFirstName ;
    String senderLastName ;
    String senderEmail;
    String accountNumber ;
    String amount ;
    String recipientFirstName ;
    String recipientLastName ;
    String recipientEmail;
    String balance ;
    String description ;
    String transactionPurpose ;
    String transactionType ;
    String timeStamp ;
    String refCode ;

    public DepositOrWithdrawEmailDTO() {

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

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
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




    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
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

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }



    @Override
    public String toString() {
        String timeStamp = new SimpleDateFormat("MMM dd, yyyy h:mm a").format(this.timeStamp);
        return "DepositOrWithdrawEmail{" +
                "senderFirstName='" + senderFirstName + '\'' +
                ", senderLastName='" + senderLastName + '\'' +
                ", senderEmail='" + senderEmail + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", recipientFirstName='" + recipientFirstName + '\'' +
                ", recipientLastName='" + recipientLastName + '\'' +
                ", recipientEmail='" + recipientEmail + '\'' +
                ", balance=" + balance +
                ", description='" + description + '\'' +
                ", transactionPurpose=" + transactionPurpose +
                ", transactionType=" + transactionType +
                ", timeStamp=" + timeStamp +
                ", refCode='" + refCode + '\'' +
                '}';
    }
}
