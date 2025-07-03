package com.olisa_td.transactionservice.jpa;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @Column(name = "user_id", nullable = false)
        private String userId;

        @Column(name = "amount")
        private BigDecimal amount;

        @Column(name = "transaction_type")
        @Enumerated(EnumType.STRING)
        private TransactionType transactionType;

        @Column(name = "account_number")
        private Long accountNumber;

        @Column(name = "email")
        private String email;

        @Column(name = "recipient_email")
        private String recipientEmail;

        @Column(name = "recipient_account_number")
        private Long recipientAccountNumber;

        @Column(name = "description",length = 5000)
        private String description;

        @Column(name = "reference_code")
        private String referenceCode;

        @Column(name = "date",nullable = false)
        private Date timeStamp;



        public Transaction() {
        }

        public UUID getId() {
                return id;
        }

        public void setId(UUID id) {
                this.id = id;
        }


        public String getUserId() {
                return userId;
        }

        public void setUserId(String userId) {
                this.userId = userId;
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

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getRecipientEmail() {
                return recipientEmail;
        }

        public void setRecipientEmail(String recipientEmail) {
                this.recipientEmail = recipientEmail;
        }

        public String getReferenceCode() {
                return referenceCode;
        }

        public void setReferenceCode(String referenceCode) {
                this.referenceCode = referenceCode;
        }

        public Date getTimeStamp() {
                return timeStamp;
        }

        public void setTimeStamp(Date timeStamp) {
                this.timeStamp = timeStamp;
        }

        @Override
        public String toString() {
                String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(this.timeStamp);
                return "Transaction{" +
                        "id=" + id +
                        ", userId='" + userId + '\'' +
                        ", amount=" + amount +
                        ", transactionType=" + transactionType +
                        ", accountNumber='" + accountNumber + '\'' +
                        ", recipientAccountNumber='" + recipientAccountNumber + '\'' +
                        ", email='" + email + '\'' +
                        ", recipientEmail='" + recipientEmail + '\'' +
                        ", referenceCode='" + referenceCode + '\'' +
                        ", description='" + description + '\'' +
                        ", timeStamp=" + timeStamp +
                        '}';
        }


}


