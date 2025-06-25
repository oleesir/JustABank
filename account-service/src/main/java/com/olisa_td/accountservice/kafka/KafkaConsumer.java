package com.olisa_td.accountservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import com.olisa_td.accountservice.exception.domain.AccountNotFoundException;
import com.olisa_td.accountservice.jpa.Account;
import com.olisa_td.accountservice.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import transaction.events.TransactionEvent;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class KafkaConsumer {

    @Autowired
    private AccountRepository accountRepository;


    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics="transaction", groupId = "account-service")
    public void consumeEvent(byte[] event) {
        try {
            TransactionEvent transactionEvent = TransactionEvent.parseFrom(event);

            Account account = accountRepository.findById(UUID.fromString(transactionEvent.getAccountId()))
                    .orElseThrow(() -> new AccountNotFoundException("Account does not exist"));

            account.setBalance(new BigDecimal(transactionEvent.getNewBalance()));

            this.accountRepository.save(account);

        } catch (InvalidProtocolBufferException e) {
            logger.error("Error deserializing event {}", e.getMessage());
        }
    }
}