package com.olisa_td.transactionservice.dto;

import com.olisa_td.transactionservice.jpa.TransactionPurpose;
import com.olisa_td.transactionservice.jpa.TransactionType;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class TrxResponseDTO {

    private  String userId;

    private Long accountNumber;

    private Long recipientAccountNumber;

    private BigDecimal amount;

    private TransactionType transactionType;

    private TransactionPurpose transactionPurpose;

    private String description;

    private String referenceCode;

    private Timestamp timeStamp;


    public TrxResponseDTO() {

    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getRecipientAccountNumber() {
        return recipientAccountNumber;
    }

    public void setRecipientAccountNumber(Long recipientAccountNumber) {
        this.recipientAccountNumber = recipientAccountNumber;
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }



    @Override
    public String toString() {
        String timeStamp = new SimpleDateFormat("MMM dd, yyyy h:mm a").format(this.timeStamp);

        return "TrxResponseDTO{" +
                "userId='" + userId + '\'' +
                ", accountNumber=" + accountNumber +
                ", recipientAccountNumber=" + recipientAccountNumber +
                ", amount=" + amount +
                ", transactionType=" + transactionType +
                ", transactionPurpose=" + transactionPurpose +
                ", description='" + description + '\'' +
                ", referenceCode='" + referenceCode + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
