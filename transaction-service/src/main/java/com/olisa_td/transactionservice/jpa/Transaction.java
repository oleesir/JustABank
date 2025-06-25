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

        @Column(name = "account_id", nullable = false)
        private String accountId;

        @Column(name = "user_id", nullable = false)
        private String userId;

        @Column(name = "amount",nullable = false)
        private BigDecimal amount;

        @Column(name = "transaction_type")
        @Enumerated(EnumType.STRING)
        private TransactionType transactionType;

        @Column(name = "status",nullable = false)
        @Enumerated(EnumType.STRING)
        private TransactionStatus transactionStatus;

        @Column(name = "description",length = 5000)
        private String description;

        @Column(name = "date",nullable = false)
        private Date timeStamp;



        public Transaction() {
                this.transactionType = TransactionType.WITHDRAW;
                this.timeStamp = new Date();
        }


        public UUID getId() {
                return id;
        }

        public void setId(UUID id) {
                this.id = id;
        }

        public String getAccountId() {
                return accountId;
        }

        public void setAccountId(String accountId) {
                this.accountId = accountId;
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

        public TransactionStatus getTransactionStatus() {
                return transactionStatus;
        }

        public void setTransactionStatus(TransactionStatus transactionStatus) {
                this.transactionStatus = transactionStatus;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public Date getTimeStamp() {
                return timeStamp;
        }


        @Override
        public String toString() {
                String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(this.timeStamp);
                return "Transaction{" +
                        "id=" + id +
                        ", accountId='" + accountId + '\'' +
                        ", userId='" + userId + '\'' +
                        ", amount=" + amount +
                        ", transactionType=" + transactionType +
                        ", transactionStatus=" + transactionStatus +
                        ", description='" + description + '\'' +
                        ", date=" + timeStamp +
                        '}';
        }
}
