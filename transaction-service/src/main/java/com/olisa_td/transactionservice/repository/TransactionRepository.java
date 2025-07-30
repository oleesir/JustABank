package com.olisa_td.transactionservice.repository;

import com.olisa_td.transactionservice.jpa.Transaction;
import com.olisa_td.transactionservice.jpa.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findByAccountNumberAndTransactionTypeAndTimeStampBetween(
            Long accountNumber,
            TransactionType transactionType,
            Timestamp start,
            Timestamp end
    );
}
