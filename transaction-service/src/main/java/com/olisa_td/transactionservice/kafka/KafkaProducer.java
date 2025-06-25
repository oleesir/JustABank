package com.olisa_td.transactionservice.kafka;

import com.olisa_td.transactionservice.jpa.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import transaction.events.TransactionEvent;
import java.math.BigDecimal;

@Service
public class KafkaProducer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;


    public void sendEvent(Transaction transaction, BigDecimal newBalance) {
        TransactionEvent event = TransactionEvent.newBuilder()
                .setAccountId(transaction.getAccountId())
                .setNewBalance(newBalance.toString())
                .setEventType("UPDATE_ACCOUNT_BALANCE")
                .build();

        try {
            kafkaTemplate.send("transaction", event.toByteArray());
        } catch (Exception e) {
            logger.error("Error sending transaction event: {}", e);
        }
    }
}