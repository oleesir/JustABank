package com.olisa_td.transactionservice.dto;


import java.math.BigDecimal;

public class ReceiversTrxEmail {
    private String transactionId;
    private BigDecimal receiverNewBalance;
    private BigDecimal amount;
    private String receiverAccountNumber;
    private String receiverFirstName;
    private String receiverLastName;
    private String receiverEmail;
    private String trxDate;

    public ReceiversTrxEmail() {

    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getReceiverNewBalance() {
        return receiverNewBalance;
    }

    public void setReceiverNewBalance(BigDecimal receiverNewBalance) {
        this.receiverNewBalance = receiverNewBalance;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public String getReceiverFirstName() {
        return receiverFirstName;
    }

    public void setReceiverFirstName(String receiverFirstName) {
        this.receiverFirstName = receiverFirstName;
    }

    public String getReceiverLastName() {
        return receiverLastName;
    }

    public void setReceiverLastName(String receiverLastName) {
        this.receiverLastName = receiverLastName;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(String trxDate) {
        this.trxDate = trxDate;
    }
}
