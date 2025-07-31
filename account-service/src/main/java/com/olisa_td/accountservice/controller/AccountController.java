package com.olisa_td.accountservice.controller;

import com.olisa_td.accountservice.domain.PageResponse;
import com.olisa_td.accountservice.dto.AccountRequest;
import com.olisa_td.accountservice.service.AccountService;
import com.olisa_td.accountservice.jpa.Account;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;


    @PostMapping("/new_account")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<Account> createAccount(@Valid @RequestBody AccountRequest accountRequest){
        return ResponseEntity.ok(this.accountService.createAccount(accountRequest));
    }


    @PatchMapping("/{id}/{status}")
    @PreAuthorize("hasAnyRole('ROLE_STAFF', 'ROLE_ADMIN')")
    public ResponseEntity<Account> updateAccountStatus(@PathVariable String id, @PathVariable String status){
        return ResponseEntity.ok(this.accountService.updateAccountStatus(id,status));
    }



    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_STAFF', 'ROLE_ADMIN')")
    public ResponseEntity<Account> getUserAccount(@PathVariable String id){
        return ResponseEntity.ok(this.accountService.getUserAccount(id));
    }



    @GetMapping("/my_accounts")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<List<Account>> getMyAccounts(){
        return ResponseEntity.ok(this.accountService.getOwnerAccounts());
    }


    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_STAFF', 'ROLE_ADMIN')")
    public ResponseEntity<PageResponse<Account>> getAllAccounts(@RequestParam int pageNum, @RequestParam int pageSize) {

        return ResponseEntity.ok(this.accountService.getAllAccounts(pageNum, pageSize));
    }

    @GetMapping("/account_number/{accountNumber}")
    public ResponseEntity<Account> getAccountNumber(@PathVariable String accountNumber){
        return ResponseEntity.ok(this.accountService.getUserAccountNum(accountNumber));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_STAFF', 'ROLE_ADMIN')")
    public ResponseEntity<String> deleteAccount(@PathVariable String id){
            this.accountService.deleteAccount(id);
            return ResponseEntity.ok("Successfully deleted.");
    }

}



