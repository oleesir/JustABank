package com.olisa_td.transactionservice.service.transaction;

import com.olisa_td.transactionservice.domain.PageResponse;
import com.olisa_td.transactionservice.dto.*;
import com.olisa_td.transactionservice.dto.DepositOrWithdrawTrxRequestDTO;
import com.olisa_td.transactionservice.dto.TrxResponseDTO;
import com.olisa_td.transactionservice.exception.domain.*;
import com.olisa_td.transactionservice.jpa.Transaction;
import com.olisa_td.transactionservice.jpa.TransactionPurpose;
import com.olisa_td.transactionservice.jpa.TransactionType;
import com.olisa_td.transactionservice.kafka.KafkaProducer;
import com.olisa_td.transactionservice.mapper.DepositOrWithdrawTrxMapper;
import com.olisa_td.transactionservice.mapper.TransferTrxMapper;
import com.olisa_td.transactionservice.repository.TransactionRepository;
import com.olisa_td.transactionservice.service.account.AccountServiceImpl;
import com.olisa_td.transactionservice.service.user.UserServiceImpl;
import com.olisa_td.transactionservice.utils.ReferenceCodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class TransactionService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${transaction.daily_limit}")
    private BigDecimal dailyLimit;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;





    public TrxResponseDTO processDepositOrWithdrawTrx(DepositOrWithdrawTrxRequestDTO depositOrWithdrawTrxRequestDTO) {
        UserResponse sender = getUserFromAuthServiceById(getUserIdCxtHolder());

        BigDecimal updatedBalance;


        AccountResponse accountResponse = getAccountByAccountNumber(depositOrWithdrawTrxRequestDTO.getAccountNumber().toString());
        if (accountResponse == null) {
            throw new AccountNotFoundException("Account not found here");
        }

        if (depositOrWithdrawTrxRequestDTO.getTransactionPurpose() == TransactionPurpose.DEPOSIT) {

            updatedBalance = accountResponse.getBalance().add(depositOrWithdrawTrxRequestDTO.getAmount());

        } else if (depositOrWithdrawTrxRequestDTO.getTransactionPurpose() == TransactionPurpose.WITHDRAW) {

            if(hasExceededDailyLimit(accountResponse.getAccountNumber(),depositOrWithdrawTrxRequestDTO.getAmount())){
                throw new DailyLimitException("Daily withdrawal limit for this account has been exceeded.");
            }

            if (accountResponse.getBalance().compareTo(depositOrWithdrawTrxRequestDTO.getAmount()) < 0) {
                throw new InsufficientFundsException("Insufficient balance for withdrawal");
            }

            updatedBalance = accountResponse.getBalance().subtract(depositOrWithdrawTrxRequestDTO.getAmount());

        } else {

            throw new InvalidTransactionException("Unsupported transaction purpose: " + depositOrWithdrawTrxRequestDTO.getTransactionPurpose());
        }

        String refCode = ReferenceCodeGenerator.RefCode(12);

        Transaction transaction = DepositOrWithdrawTrxMapper.toModel(depositOrWithdrawTrxRequestDTO,refCode);

        transaction.setUserId(accountResponse.getUserId());
        transaction.setTransactionType(
                depositOrWithdrawTrxRequestDTO.getTransactionPurpose() == TransactionPurpose.DEPOSIT ?
                        TransactionType.CREDIT : TransactionType.DEBIT
        );

        Transaction savedTrx = transactionRepository.save(transaction);

         kafkaProducer.updateAccountBalanceEvent(accountResponse.getAccountNumber(), updatedBalance,"UPDATE_ACCOUNT_BALANCE");

         UserResponse recipient = getUserFromAuthServiceById(accountResponse.getUserId());


        DepositOrWithdrawEmailDTO trxEmailDTO = new DepositOrWithdrawEmailDTO();

        trxEmailDTO.setAmount(savedTrx.getAmount().toString());
        trxEmailDTO.setSenderFirstName(sender.getFirstName());
        trxEmailDTO.setSenderLastName(sender.getLastName());
        trxEmailDTO.setSenderEmail(sender.getEmail());
        trxEmailDTO.setRecipientFirstName(recipient.getFirstName());
        trxEmailDTO.setRecipientLastName(recipient.getLastName());
        trxEmailDTO.setRecipientEmail(recipient.getEmail());
        trxEmailDTO.setBalance(updatedBalance.toString());
        trxEmailDTO.setDescription(savedTrx.getDescription());
        trxEmailDTO.setTransactionType(savedTrx.getTransactionType().toString());
        trxEmailDTO.setTransactionPurpose(savedTrx.getTransactionPurpose().toString());
        trxEmailDTO.setRefCode(savedTrx.getReferenceCode());
        trxEmailDTO.setAccountNumber(depositOrWithdrawTrxRequestDTO.getAccountNumber().toString());
        trxEmailDTO.setTimeStamp(savedTrx.getTimeStamp().toString());


        kafkaProducer.DepositOrWithdrawEmailEvent(trxEmailDTO, "DEPOSIT_OR_WITHDRAW");


        return DepositOrWithdrawTrxMapper.toDto(savedTrx);

    }




    public TrxResponseDTO processTransferTrx(TransferTrxRequestDTO transferTrxRequestDTO){

         UserResponse originUser = getUserFromAuthServiceById(getUserIdCxtHolder());

        AccountResponse senderAcct = getAccountByAccountNumber(transferTrxRequestDTO.getAccountNumber().toString());
        AccountResponse recipientAcct = getAccountByAccountNumber(transferTrxRequestDTO.getRecipientAccountNumber().toString());


        if (senderAcct == null) {
            throw new AccountNotFoundException("Account not found.");
        }

        if (recipientAcct == null) {
            throw new AccountNotFoundException("Recipient account not found.");
        }

        UserResponse destinationUser = getUserFromAuthServiceById(recipientAcct.getUserId());


        if(hasExceededDailyLimit(senderAcct.getAccountNumber(),transferTrxRequestDTO.getAmount())){
            throw new DailyLimitException("your daily limit has been exceeded.");
        }


            if (senderAcct.getBalance().compareTo(transferTrxRequestDTO.getAmount()) < 0) {
                throw new InsufficientFundsException("Insufficient balance for withdrawal.");
            }

           BigDecimal updatedSenderBalance = senderAcct.getBalance().subtract(transferTrxRequestDTO.getAmount());
           BigDecimal updatedRecipientBalance = recipientAcct.getBalance().add(transferTrxRequestDTO.getAmount());


           String refCode = ReferenceCodeGenerator.RefCode(12);
           TransactionPurpose purpose = senderAcct.getUserId().equals(recipientAcct.getUserId()) ? TransactionPurpose.INTERNAL_TRANSFER:TransactionPurpose.TRANSFER;


        Transaction toTrx = TransferTrxMapper.toModel(transferTrxRequestDTO,TransactionType.CREDIT,refCode);
        toTrx.setUserId(recipientAcct.getUserId());
        toTrx.setTransactionPurpose(purpose);
        Transaction savedRecipientTrx = transactionRepository.save(toTrx);


        Transaction fromTrx = TransferTrxMapper.toModel(transferTrxRequestDTO,TransactionType.DEBIT,refCode);
        fromTrx.setUserId(originUser.getId());;
        fromTrx.setTransactionPurpose(purpose);
        Transaction  savedSenderTrx = transactionRepository.save(fromTrx);


        kafkaProducer.updateAccountBalanceEvent(senderAcct.getAccountNumber(), updatedSenderBalance,"UPDATE_SENDER_ACCOUNT_BALANCE");
        kafkaProducer.updateAccountBalanceEvent(recipientAcct.getAccountNumber(), updatedRecipientBalance,"UPDATE_RECIPIENT_ACCOUNT_BALANCE");


        TrxEmailDTO senderEmail = new TrxEmailDTO();
        senderEmail.setBalance(updatedSenderBalance.toString());
        senderEmail.setDescription(savedSenderTrx.getDescription());
        senderEmail.setTransactionType(savedSenderTrx.getTransactionType().toString());
        senderEmail.setTransactionPurpose(savedSenderTrx.getTransactionPurpose().toString());
        senderEmail.setRefCode(savedSenderTrx.getReferenceCode());
        senderEmail.setTimeStamp(savedSenderTrx.getTimeStamp().toString());
        senderEmail.setAmount(savedSenderTrx.getAmount().toString());
        senderEmail.setSenderFirstName(originUser.getFirstName());
        senderEmail.setSenderLastName(originUser.getLastName());
        senderEmail.setSenderEmail(originUser.getEmail());
        senderEmail.setAccountNumber(senderAcct.getAccountNumber());
        senderEmail.setRecipientFirstName(destinationUser.getFirstName());
        senderEmail.setRecipientLastName(destinationUser.getLastName());
        senderEmail.setRecipientEmail(destinationUser.getEmail());
        senderEmail.setRecipientAccountNumber(recipientAcct.getAccountNumber());


        kafkaProducer.transactionEmailEvent(senderEmail,"SENDER");

        TrxEmailDTO recipientEmail = new TrxEmailDTO();
        recipientEmail.setRecipientAccountNumber(savedRecipientTrx.getRecipientAccountNumber().toString());
        recipientEmail.setBalance(updatedRecipientBalance.toString());
        recipientEmail.setDescription(savedRecipientTrx.getDescription());
        recipientEmail.setTransactionType(savedRecipientTrx.getTransactionType().toString());
        recipientEmail.setTransactionPurpose(savedRecipientTrx.getTransactionPurpose().toString());
        recipientEmail.setRefCode(savedRecipientTrx.getReferenceCode());
        recipientEmail.setRecipientFirstName(destinationUser.getFirstName());
        recipientEmail.setRecipientLastName(destinationUser.getLastName());
        recipientEmail.setRecipientEmail(destinationUser.getEmail());
        recipientEmail.setAmount(savedRecipientTrx.getAmount().toString());
        recipientEmail.setSenderFirstName(originUser.getFirstName());
        recipientEmail.setSenderLastName(originUser.getLastName());
        recipientEmail.setSenderEmail(originUser.getEmail());
        recipientEmail.setAccountNumber(senderAcct.getAccountNumber());
        recipientEmail.setRecipientAccountNumber(recipientAcct.getAccountNumber());
        recipientEmail.setTimeStamp(savedRecipientTrx.getTimeStamp().toString());



        kafkaProducer.transactionEmailEvent(recipientEmail,"RECIPIENT");

        return TransferTrxMapper.toDto(savedSenderTrx);

    }


    public PageResponse<Transaction> getAllTransactions(int pageNum, int pageSize) {

        if (pageNum < 1) {
            throw new IllegalArgumentException("Page number must be greater than 0.");
        }
        if (pageSize < 1) {
            throw new IllegalArgumentException("Page size must be greater than 0.");
        }

        Page<Transaction> paged = this.transactionRepository.findAll(PageRequest.of(pageNum - 1, pageSize, Sort.by("timeStamp").descending()));

        return new PageResponse<Transaction>(paged);
    }




    public PageResponse<Transaction> getAllTransactionsByUserId(int pageNum, int pageSize) {
       String userId = getUserIdCxtHolder();

        if (pageNum < 1) {
            throw new IllegalArgumentException("Page number must be greater than 0.");
        }
        if (pageSize < 1) {
            throw new IllegalArgumentException("Page size must be greater than 0.");
        }

        Page<Transaction> paged = this.transactionRepository.findAllByUserId(userId,PageRequest.of(pageNum - 1, pageSize, Sort.by("timeStamp").descending()));

        return new PageResponse<Transaction>(paged);
    }


    public Transaction getTransaction(String id){

        return transactionRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new TransactionNotFoundException("Transaction does not exist"));
    }



    private String getUserIdCxtHolder () {

            return SecurityContextHolder.getContext().getAuthentication().getName();
        }


    private UserResponse getUserFromAuthServiceById (String id){
            return userServiceImpl.getUser("/users/{id}", id).block();
        }


    private AccountResponse getAccountByAccountNumber (String num){
            return accountServiceImpl.getAccount("/account_number/{accountNumber}", num).block();
        }


    private boolean hasExceededDailyLimit(String accountNumber, BigDecimal amount){
        Timestamp startOfDay = Timestamp.valueOf(LocalDate.now().atStartOfDay());
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());


        List<Transaction> usersTransactions = transactionRepository.findByAccountNumberAndTransactionTypeAndTimeStampBetween(Long.parseLong(accountNumber), TransactionType.DEBIT, startOfDay, currentTime);

        BigDecimal totalDebitSoFar = usersTransactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal projectedTotal = totalDebitSoFar.add(amount);

        return projectedTotal.compareTo(dailyLimit) > 0;
      }

    }
