package com.olisa_td.transactionservice.mapper;

import com.olisa_td.transactionservice.dto.DepositOrWithdrawTrxRequestDTO;
import com.olisa_td.transactionservice.dto.TrxResponseDTO;
import com.olisa_td.transactionservice.jpa.Transaction;
import java.sql.Timestamp;
import java.time.Instant;


public class DepositOrWithdrawTrxMapper {

    public static TrxResponseDTO toDto(Transaction transaction) {
        TrxResponseDTO trxResponseDTO = new TrxResponseDTO();

        trxResponseDTO.setUserId(transaction.getUserId());
        trxResponseDTO.setAmount(transaction.getAmount());
        trxResponseDTO.setTransactionPurpose(transaction.getTransactionPurpose());
        trxResponseDTO.setTransactionType(transaction.getTransactionType());
        trxResponseDTO.setReferenceCode(transaction.getReferenceCode());
        trxResponseDTO.setDescription(transaction.getDescription());
        trxResponseDTO.setAccountNumber(transaction.getAccountNumber());
        trxResponseDTO.setTimeStamp(transaction.getTimeStamp());


        return trxResponseDTO;
    }

    public static Transaction toModel( DepositOrWithdrawTrxRequestDTO depositOrWithdrawTrxRequestDTO, String refCode){
        Transaction transaction = new Transaction();

        transaction.setAmount(depositOrWithdrawTrxRequestDTO.getAmount());
        transaction.setTransactionPurpose(depositOrWithdrawTrxRequestDTO.getTransactionPurpose());
        transaction.setDescription(depositOrWithdrawTrxRequestDTO.getDescription());
        transaction.setReferenceCode(refCode);
        transaction.setTimeStamp(Timestamp.from(Instant.now()));
        transaction.setAccountNumber(depositOrWithdrawTrxRequestDTO.getAccountNumber());

        return transaction;

    }
}
