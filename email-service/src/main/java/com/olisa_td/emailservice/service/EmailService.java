package com.olisa_td.emailservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import jakarta.mail.internet.MimeMessage;
import org.thymeleaf.context.Context;
import transaction.events.DepositOrWithdrawTrxEvent;
import transaction.events.TransferTrxEvent;


@Service
public class EmailService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;


    public void transferEmail(TransferTrxEvent transferTrxEvent, String templateName) {
        try {
                Context context = new Context();
                context.setVariable("transferTrxEvent", transferTrxEvent);
            String process = this.templateEngine.process(templateName, context);


                MimeMessage senderMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper senderHelper = new MimeMessageHelper(senderMessage, true);
                senderHelper.setFrom(emailFrom, "Just A Bank");
                senderHelper.setTo(transferTrxEvent.getTransactionType().equals("DEBIT")? transferTrxEvent.getSenderEmail() : transferTrxEvent.getRecipientEmail());
                senderHelper.setSubject(transferTrxEvent.getTransactionType().equals("DEBIT")? "Debit Alert" : "Credit Alert");
                senderHelper.setText(process, true);

                javaMailSender.send(senderMessage);


        } catch (Exception ex) {
            logger.error("Error while sending emails to sender/recipient: {}", ex.getMessage(), ex);
        }
    }



    public void depositOrWithdrawEmail(DepositOrWithdrawTrxEvent depositOrWithdrawTrxEvent, String templateName) {
        try {
            Context context = new Context();
            context.setVariable("depositOrWithdrawTrxEvent", depositOrWithdrawTrxEvent);
            String process = this.templateEngine.process(templateName, context);


            MimeMessage senderMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper senderHelper = new MimeMessageHelper(senderMessage, true);
            senderHelper.setFrom(emailFrom, "Just A Bank");
            String[] emails = new String[] {
                    depositOrWithdrawTrxEvent.getSenderEmail(),
                    depositOrWithdrawTrxEvent.getRecipientEmail()
            };
            senderHelper.setTo(emails);
            senderHelper.setSubject("Transaction complete");
            senderHelper.setText(process, true);

            javaMailSender.send(senderMessage);


        } catch (Exception ex) {
            logger.error("Error while sending emails to sender/recipient: {}", ex.getMessage(), ex);
        }
    }


}
