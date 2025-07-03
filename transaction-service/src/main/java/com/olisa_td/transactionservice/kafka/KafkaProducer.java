package com.olisa_td.transactionservice.kafka;

import com.olisa_td.transactionservice.dto.ReceiversTrxEmail;
import com.olisa_td.transactionservice.jpa.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import transaction.events.ReceiversTrxEmailEvent;
import transaction.events.UpdateAccountBalanceEvent;

import java.math.BigDecimal;

@Service
public class KafkaProducer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;


    public void updateAccountBalanceEvent(String accountNumber, BigDecimal newBalance) {
        UpdateAccountBalanceEvent event = UpdateAccountBalanceEvent.newBuilder()
                .setAccountNumber(accountNumber)
                .setNewBalance(newBalance.toString())
                .build();

        sendEvent("transaction",event);
    }



    public void sendTransactionEmailEvent(ReceiversTrxEmail receiversTrxEmail, BigDecimal newBalance) {
        ReceiversTrxEmailEvent event = ReceiversTrxEmailEvent.newBuilder()
                .setTransactionId(receiversTrxEmail.getTransactionId())
                .setReceiverAccountNumber(receiversTrxEmail.getReceiverAccountNumber())
                .setReceiverNewBalance(newBalance.toString())
                .setAmount(receiversTrxEmail.getAmount().toString())
                .setEventDate(receiversTrxEmail.getTrxDate())
                .setReceiverFirstName(receiversTrxEmail.getReceiverFirstName())
                .setReceiverLastName(receiversTrxEmail.getReceiverLastName())
                .setReceiverEmail(receiversTrxEmail.getReceiverEmail())
                .build();


      sendEvent("transaction",event);
    }


    private void sendEvent(String topic, Object event) {
        try {
            kafkaTemplate.send(topic, ((com.google.protobuf.Message) event).toByteArray());
            logger.info("Event sent: {}", event);
        } catch (Exception e) {
            logger.error("Error sending {} event: {}", event, e.getMessage());
        }
    }


}