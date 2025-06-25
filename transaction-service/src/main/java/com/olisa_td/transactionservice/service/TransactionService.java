package com.olisa_td.transactionservice.service;

import com.olisa_td.transactionservice.dto.AccountResponse;
import com.olisa_td.transactionservice.dto.TransactionRequest;
import com.olisa_td.transactionservice.exception.domain.AccountNotFoundException;
import com.olisa_td.transactionservice.exception.domain.InsufficientFundsException;
import com.olisa_td.transactionservice.exception.domain.InvalidTransactionException;
import com.olisa_td.transactionservice.jpa.Transaction;
import com.olisa_td.transactionservice.jpa.TransactionStatus;
import com.olisa_td.transactionservice.jpa.TransactionType;
import com.olisa_td.transactionservice.kafka.KafkaProducer;
import com.olisa_td.transactionservice.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;



@Service
public class TransactionService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WebClient webClient;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    KafkaProducer kafkaProducer;




    public Transaction initiateTransaction( TransactionRequest transactionRequest) {

    AccountResponse accountResponse = getAccountById(transactionRequest.getId()).block();
    TransactionType typeOfTransaction;

        try {
            typeOfTransaction = TransactionType.valueOf(transactionRequest.getTransactionType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidTransactionException("Invalid transaction type: " + transactionRequest.getTransactionType());
        }


    if (accountResponse == null) {
        throw new AccountNotFoundException("Account not found");
    }


    BigDecimal newBalance = calculateNewBalance(accountResponse.getBalance(), transactionRequest.getAmount(), typeOfTransaction);

        Transaction transaction = new Transaction();

        transaction.setAccountId(transactionRequest.getId());
        transaction.setUserId(getUserId());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setTransactionType(typeOfTransaction);
        transaction.setDescription(transactionRequest.getDescription());

    Transaction newTransaction = this.transactionRepository.save(transaction);

        this.kafkaProducer.sendEvent(newTransaction, newBalance);

        return newTransaction;

    }


    private BigDecimal calculateNewBalance(BigDecimal currentBalance, BigDecimal amount, TransactionType typeOfTransaction) {

        switch (typeOfTransaction) {
            case DEPOSIT:
                return currentBalance.add(amount);
            case WITHDRAW:
                if (currentBalance.compareTo(amount) < 0) {
                    throw new InsufficientFundsException("Insufficient funds for this transaction");
                }
                return currentBalance.subtract(amount);
            default:
               throw new InvalidTransactionException("Unsupported transaction type");

        }
    }


    private Mono<AccountResponse> getAccountById(String id) {
        return this.webClient
                .get()
                .uri("/{id}",id)
                .retrieve()
                .bodyToMono(AccountResponse.class)
                .onErrorResume(WebClientResponseException.class, error -> Mono.error(new AccountNotFoundException("Account not found")));

    }

    private String getUserId(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
