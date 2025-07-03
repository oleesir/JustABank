package com.olisa_td.transactionservice.service.transaction;

import com.olisa_td.transactionservice.dto.*;
import com.olisa_td.transactionservice.dto.fundOrWithdraw.FundOrWithdrawRequestDTO;
import com.olisa_td.transactionservice.dto.fundOrWithdraw.FundOrWithdrawResponseDTO;
import com.olisa_td.transactionservice.dto.moveFund.MoveFundRequestDTO;
import com.olisa_td.transactionservice.dto.moveFund.MoveFundResponseDTO;
import com.olisa_td.transactionservice.exception.domain.AccountNotFoundException;
import com.olisa_td.transactionservice.exception.domain.InsufficientFundsException;
import com.olisa_td.transactionservice.exception.domain.InvalidTransactionException;
import com.olisa_td.transactionservice.jpa.Transaction;
import com.olisa_td.transactionservice.jpa.TransactionType;
import com.olisa_td.transactionservice.kafka.KafkaProducer;
import com.olisa_td.transactionservice.mapper.FundOrWithdraw;
import com.olisa_td.transactionservice.repository.TransactionRepository;
import com.olisa_td.transactionservice.service.account.AccountServiceImpl;
import com.olisa_td.transactionservice.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    TransactionType typeOfTransaction;

    @Autowired
    private UserServiceImpl userServiceImpl;


    private ReceiversTrxEmail receiversTrxEmail;


    public FundOrWithdrawResponseDTO processTransaction(FundOrWithdrawRequestDTO fundOrWithdrawRequestDTO) {
        String userId = getUserId();
        BigDecimal updatedBalance;


        AccountResponse accountResponse = getAccountByAccountNumber(fundOrWithdrawRequestDTO.getAccountNumber().toString());

        if (accountResponse == null) {
            throw new AccountNotFoundException("Account not found");
        }

        if (fundOrWithdrawRequestDTO.getTransactionType() == TransactionType.FUND) {
            updatedBalance = accountResponse.getBalance().add(fundOrWithdrawRequestDTO.getAmount());
        } else if (fundOrWithdrawRequestDTO.getTransactionType() == TransactionType.WITHDRAW) {
            if (accountResponse.getBalance().compareTo(fundOrWithdrawRequestDTO.getAmount()) < 0) {
                throw new InsufficientFundsException("Insufficient balance for withdrawal");
            }
            updatedBalance = accountResponse.getBalance().subtract(fundOrWithdrawRequestDTO.getAmount());
        } else {
            throw new InvalidTransactionException("Unsupported transaction type: " + fundOrWithdrawRequestDTO.getTransactionType());
        }


        Transaction transaction = FundOrWithdraw.toModel(fundOrWithdrawRequestDTO);

        transaction.setUserId(userId);

        Transaction savedTransaction = transactionRepository.save(transaction);

         kafkaProducer.updateAccountBalanceEvent(accountResponse.getAccountNumber(), updatedBalance);

        return FundOrWithdraw.toDto(savedTransaction);

    }


//
//    public MoveFundResponseDTO moveFundTransaction(MoveFundRequestDTO moveFundRequestDTO) {
//        String userId = getUserId();
//        BigDecimal updatedBalance;
//
//
//        AccountResponse accountResponse = getAccountByAccountNumber(moveFundRequestDTO.getAccountNumber().toString());
//        AccountResponse recipientAccountResponse = getAccountByAccountNumber(moveFundRequestDTO.getRecipientAccountNumber().toString());
//
//
//        if (accountResponse == null) {
//            throw new AccountNotFoundException("Account not found");
//        }
//
//        if(!recipientAccountResponse.getUserId().equals(userId)){
//            throw new AccountNotFoundException("Account does not belong to you.");
//        }
//
//
//        if (accountResponse.getBalance().compareTo(moveFundRequestDTO.getAmount()) < 0) {
//               throw new InsufficientFundsException("Insufficient balance for transfer");
//           }
//
//
//       BigDecimal debitAccountBalance = accountResponse.getBalance().subtract(moveFundRequestDTO.getAmount());
//        BigDecimal creditAccountBalance = recipientAccountResponse.getBalance().subtract(moveFundRequestDTO.getAmount());
//
//
//
//
////            updatedBalance = accountResponse.getBalance().subtract(fundOrWithdrawRequestDTO.getAmount());
////        if (fundOrWithdrawRequestDTO.getTransactionType() == TransactionType.FUND) {
////            updatedBalance = accountResponse.getBalance().add(fundOrWithdrawRequestDTO.getAmount());
////        } else if (fundOrWithdrawRequestDTO.getTransactionType() == TransactionType.WITHDRAW) {
////            if (accountResponse.getBalance().compareTo(fundOrWithdrawRequestDTO.getAmount()) < 0) {
////                throw new InsufficientFundsException("Insufficient balance for withdrawal");
////            }
////            updatedBalance = accountResponse.getBalance().subtract(fundOrWithdrawRequestDTO.getAmount());
////        } else {
////            throw new InvalidTransactionException("Unsupported transaction type: " + fundOrWithdrawRequestDTO.getTransactionType());
////        }
////
////
////        Transaction transaction = FundOrWithdraw.toModel(fundOrWithdrawRequestDTO);
////
////        transaction.setUserId(userId);
////
////        Transaction savedTransaction = transactionRepository.save(transaction);
////
////        kafkaProducer.updateAccountBalanceEvent(accountResponse.getAccountNumber(), updatedBalance);
////
////        return FundOrWithdraw.toDto(savedTransaction);
//
//    }




    private String getUserId () {

            return SecurityContextHolder.getContext().getAuthentication().getName();
        }


        private AccountResponse getAccountById (String id){

            return accountServiceImpl.getAccount("/{id}", id).block();
        }


        private UserResponse getUserById (String id){
            return userServiceImpl.getUser("/transfer/{id}", id).block();
        }


        private AccountResponse getAccountByAccountNumber (String num){
            return accountServiceImpl.getAccount("/account_number/{accountNumber}", num).block();
        }


        private void validateTransactionType (String request){
            try {
                typeOfTransaction = TransactionType.valueOf(request.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidTransactionException("Invalid transaction type: " + request);
            }
        }

    }
