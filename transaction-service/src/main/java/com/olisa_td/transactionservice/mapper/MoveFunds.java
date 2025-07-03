package com.olisa_td.transactionservice.mapper;

import com.olisa_td.transactionservice.dto.fundOrWithdraw.FundOrWithdrawResponseDTO;
import com.olisa_td.transactionservice.dto.moveFund.MoveFundRequestDTO;
import com.olisa_td.transactionservice.dto.moveFund.MoveFundResponseDTO;
import com.olisa_td.transactionservice.jpa.Transaction;
import com.olisa_td.transactionservice.jpa.TransactionType;

import java.util.Date;

public class MoveFunds {

    public static MoveFundResponseDTO toDto(Transaction transaction) {
        MoveFundResponseDTO moveFundResponseDTO = new  MoveFundResponseDTO();
        moveFundResponseDTO.setAccountNumber(transaction.getAccountNumber());
        moveFundResponseDTO.setAmount(transaction.getAmount());
        moveFundResponseDTO.setTransactionType(transaction.getTransactionType());

        return moveFundResponseDTO ;
    }

    public static Transaction toModel(MoveFundRequestDTO moveFundRequestDTO){
        Transaction transaction = new Transaction();
        transaction.setAmount(moveFundRequestDTO.getAmount());
        transaction.setTransactionType(TransactionType.MOVE);
        transaction.setDescription(null);
        transaction.setAccountNumber(moveFundRequestDTO.getAccountNumber());
        transaction.setRecipientAccountNumber(moveFundRequestDTO.getRecipientAccountNumber());
        transaction.setEmail(null);
        transaction.setRecipientEmail(null);
        transaction.setReferenceCode(null);
        transaction.setTimeStamp(new Date());

        return transaction;

    }
}
