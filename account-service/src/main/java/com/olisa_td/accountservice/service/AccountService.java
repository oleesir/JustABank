package com.olisa_td.accountservice.service;


import com.olisa_td.accountservice.domain.PageResponse;
import com.olisa_td.accountservice.dto.AccountRequest;
import com.olisa_td.accountservice.exception.domain.AccountNotFoundException;
import com.olisa_td.accountservice.exception.domain.InvalidAccountException;
import com.olisa_td.accountservice.jpa.Account;
import com.olisa_td.accountservice.jpa.AccountStatus;
import com.olisa_td.accountservice.jpa.AccountType;
import com.olisa_td.accountservice.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;


@Service
public class AccountService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private  AccountRepository accountRepository;


    public Account createAccount(AccountRequest accountRequest){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        int generatedNum = generateAccountNumber();
        AccountType typeOfAccount;

        try {
            typeOfAccount = AccountType.valueOf(accountRequest.getAccountType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidAccountException("Invalid account type: " + accountRequest.getAccountType());
        }

        Account account = new Account();

        account.setUserId(userId);
        account.setAccountNumber(String.valueOf(generatedNum));
        account.setBalance(BigDecimal.valueOf(0.00));
        account.setAccountType(typeOfAccount);
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setTimeStamp(Timestamp.from(Instant.now()));

        return this.accountRepository.save(account);

    }



    public void deleteAccount(String id) {

        Account account = accountRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new AccountNotFoundException("Account does not exist"));

        this.accountRepository.delete(account);

    }

    public PageResponse<Account> getAllAccounts(int pageNum, int pageSize) {
        if (pageNum < 0) {
             throw new IllegalArgumentException("Page number must be greater than 0.");
        }
        if (pageSize <= 0) {
            throw new IllegalArgumentException("Page size must be greater than 0.");
        }

        Page<Account> paged = this.accountRepository.findAll(PageRequest.of(pageNum - 1, pageSize, Sort.by("timeStamp").descending()));

        return new PageResponse<Account>(paged);
    }

    public List<Account> getOwnerAccounts(){
        return accountRepository.findAllByUserId(getUserId());

    }


    public Account getOwnerAccount(String id){
        return accountRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new AccountNotFoundException("Account does not exist"));
    }


    public Account getUserAccount(String accountNumber){
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account does not exist"));
    }


    public Account updateAccountStatus(String id, String status) {

        Account account = accountRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new AccountNotFoundException("Account does not exist"));

        if (account.getAccountStatus() == AccountStatus.valueOf(status.toUpperCase())) {
            return account;

        }

        account.setAccountStatus(AccountStatus.valueOf(status.toUpperCase()));
        return this.accountRepository.save(account);
    }


    private int generateAccountNumber(){
        Random random = new Random();
        return 10000000 + random.nextInt(90000000);
    }

    private String getUserId () {

        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
