package com.olisa_td.transactionservice.mapper;

import com.olisa_td.transactionservice.dto.fundOrWithdraw.FundOrWithdrawRequestDTO;
import com.olisa_td.transactionservice.dto.fundOrWithdraw.FundOrWithdrawResponseDTO;
import com.olisa_td.transactionservice.jpa.Transaction;

import java.util.Date;

public class FundOrWithdraw {
    public static FundOrWithdrawResponseDTO toDto(Transaction transaction) {
        FundOrWithdrawResponseDTO fundOrWithdrawResponseDTO = new FundOrWithdrawResponseDTO();

        fundOrWithdrawResponseDTO.setAccountNumber(transaction.getAccountNumber());
        fundOrWithdrawResponseDTO.setAmount(transaction.getAmount());
        fundOrWithdrawResponseDTO.setTransactionType(transaction.getTransactionType());

        return fundOrWithdrawResponseDTO;
    }

    public static Transaction toModel( FundOrWithdrawRequestDTO fundOrWithdrawRequestDTO){
        Transaction transaction = new Transaction();
        transaction.setAmount(fundOrWithdrawRequestDTO.getAmount());
        transaction.setTransactionType(fundOrWithdrawRequestDTO.getTransactionType());
        transaction.setDescription(null);
        transaction.setAccountNumber(fundOrWithdrawRequestDTO.getAccountNumber());
        transaction.setRecipientAccountNumber(null);
        transaction.setEmail(null);
        transaction.setRecipientEmail(null);
        transaction.setReferenceCode(null);
        transaction.setTimeStamp(new Date());

        return transaction;

    }

}
