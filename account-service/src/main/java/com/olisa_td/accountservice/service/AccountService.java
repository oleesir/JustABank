package com.olisa_td.accountservice.service;


import com.olisa_td.accountservice.exception.domain.AccountNotFoundException;
import com.olisa_td.accountservice.exception.domain.NotEnoughPermissionException;
import com.olisa_td.accountservice.jpa.Account;
import com.olisa_td.accountservice.jpa.AccountStatus;
import com.olisa_td.accountservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;


@Service
public class AccountService {

    @Autowired
    private  AccountRepository accountRepository;


    public Account createAccount(Account account){

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();


        int generatedNum = generateAccountNumber();

        account.setUserId(userId);
        account.setAccountNumber(generatedNum);
        account.setBalance(BigDecimal.valueOf(0.00));
        account.setAccountStatus(AccountStatus.OPEN);

        return this.accountRepository.save(account);

    }


//    public void deleteAccountByNumber(String id, String role) {
//
//        if (!role.equalsIgnoreCase("ADMIN")) {
//            throw new NotEnoughPermissionException("You are not authorized to access this resource.");
//        }
//
//        Account account = accountRepository.findById(UUID.fromString(id))
//                .orElseThrow(() -> new AccountNotFoundException("Account does not exist"));
//
//        accountRepository.delete(account);
//
//    }

//    public void toggleAccount(String id, String role) {
//
//        if (!role.equalsIgnoreCase("ADMIN")) {
//            throw new NotEnoughPermissionException("You are not authorized to access this resource.");
//        }
//
//        Account account = accountRepository.findById(UUID.fromString(id))
//                .orElseThrow(() -> new AccountNotFoundException("Account does not exist"));
//
//        if(account.getAccountStatus().equals(AccountStatus.OPEN)){
//            account.setAccountStatus(AccountStatus.CLOSED);
//        }
//
//        if(account.getAccountStatus().equals(AccountStatus.CLOSED)){
//            account.setAccountStatus(AccountStatus.OPEN);
//        }
//
//        accountRepository.save(account);
//
//    }


    private int generateAccountNumber(){
        Random random = new Random();
        return 10000000 + random.nextInt(90000000);
    }
}
