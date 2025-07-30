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
import transaction.events.UpdateAccountBalanceEvent;
import java.math.BigDecimal;

@Service
public class KafkaConsumer {

    @Autowired
    private AccountRepository accountRepository;


    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics="trx.updateBalance", groupId = "account-service")
    public void consumeEvent(byte[] event) {
        try {
            UpdateAccountBalanceEvent updateAccountBalanceEvent = UpdateAccountBalanceEvent.parseFrom(event);

            Account account = accountRepository.findByAccountNumber(updateAccountBalanceEvent.getAccountNumber())
                    .orElseThrow(() -> new AccountNotFoundException("Account does not exist"));

            account.setBalance(new BigDecimal(updateAccountBalanceEvent.getNewBalance()));

            this.accountRepository.save(account);


        } catch (InvalidProtocolBufferException e) {
            logger.error("Error deserializing event {}", e.getMessage());
        }
    }
}