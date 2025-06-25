package com.olisa_td.accountservice.controller;

import com.olisa_td.accountservice.dto.AccountRequest;
import com.olisa_td.accountservice.service.AccountService;
import com.olisa_td.accountservice.jpa.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;


    @PostMapping("/new_account")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<Account> createAccount(@RequestBody AccountRequest accountRequest){
        return ResponseEntity.ok(this.accountService.createAccount(accountRequest));
    }


    @PatchMapping("/{id}/{status}")
    @PreAuthorize("hasAnyRole('ROLE_STAFF', 'ROLE_ADMIN')")
    public ResponseEntity<Account> updateAccountStatus(@PathVariable String id, @PathVariable String status){
        return ResponseEntity.ok(this.accountService.updateAccountStatus(id,status));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable String id){
        return ResponseEntity.ok(this.accountService.getAccount(id));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_STAFF', 'ROLE_ADMIN')")
    public ResponseEntity<String> deleteAccount(@PathVariable String id){
            this.accountService.deleteAccount(id);
            return ResponseEntity.ok("Successfully deleted.");
    }

}



