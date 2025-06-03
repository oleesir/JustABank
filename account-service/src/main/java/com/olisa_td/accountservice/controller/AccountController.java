package com.olisa_td.accountservice.controller;

import com.olisa_td.accountservice.service.AccountService;
import com.olisa_td.accountservice.jpa.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;


    @PostMapping("/new_account")
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
        Account savedAccount = this.accountService.createAccount(account);
        return ResponseEntity.ok(savedAccount );
    }

//    @DeleteMapping("/{id}")
//    public void deleteAccount(@PathVariable String id, @RequestHeader("X-Role") String role){
//            this.accountService.deleteAccountByNumber(id,role);
//
//    }


//    @PatchMapping("/{id}")
//    public void ToggleAccount(@PathVariable String id, @RequestHeader("X-Role") String role){
//        this.accountService.toggleAccount(id,role);
//    }


}
