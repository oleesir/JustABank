package com.olisa_td.transactionservice.kafka;


import com.olisa_td.transactionservice.dto.DepositOrWithdrawEmailDTO;
import com.olisa_td.transactionservice.dto.TrxEmailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import transaction.events.DepositOrWithdrawTrxEvent;
import transaction.events.TransferTrxEvent;
import transaction.events.TrxEventWrapper;
import transaction.events.UpdateAccountBalanceEvent;
import java.math.BigDecimal;


@Service
public class KafkaProducer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;


    public void updateAccountBalanceEvent(String accountNumber, BigDecimal newBalance,String eventType) {
        UpdateAccountBalanceEvent event = UpdateAccountBalanceEvent.newBuilder()
                .setAccountNumber(accountNumber)
                .setNewBalance(newBalance.toString())
                .setEventType(eventType)
                .build();

        sendEvent("trx.updateBalance",event);
    }



    public void DepositOrWithdrawEmailEvent(DepositOrWithdrawEmailDTO depositOrWithdrawEmailDTO, String eventType){
            DepositOrWithdrawTrxEvent depositOrWithdrawTrxEvent = DepositOrWithdrawTrxEvent.newBuilder()
                    .setSenderFirstName(sanitizeString(depositOrWithdrawEmailDTO.getSenderFirstName()))
                .setSenderLastName(sanitizeString(depositOrWithdrawEmailDTO.getSenderLastName()))
                .setAccountNumber(sanitizeString(depositOrWithdrawEmailDTO.getAccountNumber()))
                .setAmount(sanitizeString(depositOrWithdrawEmailDTO.getAmount()))
                .setRecipientFirstName(sanitizeString(depositOrWithdrawEmailDTO.getRecipientFirstName()))
                .setRecipientLastName(sanitizeString(depositOrWithdrawEmailDTO.getRecipientLastName()))
                .setBalance(sanitizeString(depositOrWithdrawEmailDTO.getBalance()))
                .setDescription(sanitizeString(depositOrWithdrawEmailDTO.getDescription()))
                .setTransactionPurpose(sanitizeString(depositOrWithdrawEmailDTO.getTransactionPurpose()))
                .setTransactionType(sanitizeString(depositOrWithdrawEmailDTO.getTransactionType()))
                .setTimeStamp(sanitizeString(depositOrWithdrawEmailDTO.getTimeStamp()))
                .setRefCode(sanitizeString(depositOrWithdrawEmailDTO.getRefCode()))
                .setSenderEmail(sanitizeString(depositOrWithdrawEmailDTO.getSenderEmail()))
                .setRecipientEmail(sanitizeString(depositOrWithdrawEmailDTO.getRecipientEmail()))
                .setEventType(eventType)
                    .build();



        TrxEventWrapper wrapper = TrxEventWrapper.newBuilder()
                .setDepositOrWithdrawTrxEvent(depositOrWithdrawTrxEvent)
                .build();

        sendEvent("transaction", wrapper );

    }


    public void transactionEmailEvent(TrxEmailDTO trxEmailDTO, String eventType) {
            TransferTrxEvent  event = TransferTrxEvent.newBuilder()
                    .setSenderFirstName(sanitizeString(trxEmailDTO.getSenderFirstName()))
                    .setSenderLastName(sanitizeString(trxEmailDTO.getSenderLastName()))
                    .setAccountNumber(sanitizeString(trxEmailDTO.getAccountNumber()))
                    .setAmount(sanitizeString(trxEmailDTO.getAmount()))
                    .setRecipientFirstName(sanitizeString(trxEmailDTO.getRecipientFirstName()))
                    .setRecipientLastName(sanitizeString(trxEmailDTO.getRecipientLastName()))
                    .setRecipientAccountNumber(sanitizeString(trxEmailDTO.getRecipientAccountNumber()))
                    .setBalance(sanitizeString(trxEmailDTO.getBalance()))
                    .setDescription(sanitizeString(trxEmailDTO.getDescription()))
                    .setTransactionPurpose(sanitizeString(trxEmailDTO.getTransactionPurpose()))
                    .setTransactionType(sanitizeString(trxEmailDTO.getTransactionType()))
                    .setTimeStamp(sanitizeString(trxEmailDTO.getTimeStamp()))
                    .setRefCode(sanitizeString(trxEmailDTO.getRefCode()))
                    .setSenderEmail(sanitizeString(trxEmailDTO.getSenderEmail()))
                    .setRecipientEmail(sanitizeString(trxEmailDTO.getRecipientEmail()))
                    .setEventType(eventType)
                    .build();


        TrxEventWrapper wrapper = TrxEventWrapper.newBuilder()
                .setTransferTrxEvent(event)
                .build();

            sendEvent("transaction", wrapper );

    }


    private void sendEvent(String topic,Object event) {
        try {
            kafkaTemplate.send(topic, ((com.google.protobuf.Message) event).toByteArray());
            logger.info("Event sent: {}", event);
        } catch (Exception e) {
            logger.error("Error sending {} event: {}", event, e.getMessage());
        }
    }


    private String sanitizeString(String value){
        return value != null ? value : "";
    }

}