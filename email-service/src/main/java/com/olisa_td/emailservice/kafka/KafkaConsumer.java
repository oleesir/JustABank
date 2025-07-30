package com.olisa_td.emailservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import com.olisa_td.emailservice.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import transaction.events.DepositOrWithdrawTrxEvent;
import transaction.events.TransferTrxEvent;
import transaction.events.TrxEventWrapper;


@Service
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    EmailService emailService;

    @KafkaListener(topics="transaction", groupId = "email-service")
    public void consumeEvent(byte[] event) {

        try {

            TrxEventWrapper trxEventWrapper = TrxEventWrapper.parseFrom(event);

          logger.debug("WHY MAIL {}", trxEventWrapper.getEventCase());

            switch (trxEventWrapper.getEventCase()){
                case DEPOSITORWITHDRAWTRXEVENT:
                    DepositOrWithdrawTrxEvent depositOrWithdrawTrxEvent = trxEventWrapper.getDepositOrWithdrawTrxEvent();
                    this.emailService.depositOrWithdrawEmail(depositOrWithdrawTrxEvent, "deposit_or_withdraw_trx_template");
                    break;
                case TRANSFERTRXEVENT:
                    TransferTrxEvent transferTrxEvent = trxEventWrapper.getTransferTrxEvent();
                    logger.debug("CHECK FOR MAILING {}", transferTrxEvent);
                    this.emailService.transferEmail(transferTrxEvent, "transfer_trx_template");
                    break;
                default:
                    logger.warn("Received event with no set case.");
                    break;

            }


        } catch (InvalidProtocolBufferException e) {
            logger.error("Error deserializing event {}", e.getMessage());
        }
    }



}