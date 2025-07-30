package com.olisa_td.transactionservice.mapper;

import com.olisa_td.transactionservice.dto.TransferTrxRequestDTO;
import com.olisa_td.transactionservice.dto.TrxResponseDTO;
import com.olisa_td.transactionservice.jpa.Transaction;
import com.olisa_td.transactionservice.jpa.TransactionType;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

public class TransferTrxMapper {

    public static TrxResponseDTO toDto(Transaction transaction) {
        TrxResponseDTO trxResponseDTO = new TrxResponseDTO();

        trxResponseDTO.setUserId(transaction.getUserId());
        trxResponseDTO.setAccountNumber(transaction.getAccountNumber());
        trxResponseDTO.setAmount(transaction.getAmount());
        trxResponseDTO.setRecipientAccountNumber(transaction.getRecipientAccountNumber());
        trxResponseDTO.setTransactionPurpose(transaction.getTransactionPurpose());
        trxResponseDTO.setReferenceCode(transaction.getReferenceCode());
        trxResponseDTO.setDescription(transaction.getDescription());
        trxResponseDTO.setTransactionType(transaction.getTransactionType());
        trxResponseDTO.setTimeStamp(Timestamp.from(Instant.now()));

        return trxResponseDTO;
    }



    public static Transaction toModel(TransferTrxRequestDTO transferTrxRequestDTO, TransactionType transactionType, String refCode){
        Transaction transaction = new Transaction();
        transaction.setAmount(transferTrxRequestDTO.getAmount());
        transaction.setDescription(transferTrxRequestDTO.getDescription());
        transaction.setAccountNumber(transferTrxRequestDTO.getAccountNumber());
        transaction.setRecipientAccountNumber(transferTrxRequestDTO.getRecipientAccountNumber());
        transaction.setTransactionType(transactionType);
        transaction.setReferenceCode(refCode);
        transaction.setTimeStamp(Timestamp.from(Instant.now()));

        return transaction;

    }


}
