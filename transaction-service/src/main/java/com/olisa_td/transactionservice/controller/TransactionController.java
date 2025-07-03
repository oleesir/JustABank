package com.olisa_td.transactionservice.controller;

import com.olisa_td.transactionservice.dto.fundOrWithdraw.FundOrWithdrawRequestDTO;
import com.olisa_td.transactionservice.dto.fundOrWithdraw.FundOrWithdrawResponseDTO;
import com.olisa_td.transactionservice.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/new_transaction")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<FundOrWithdrawResponseDTO> createTransaction(@RequestBody FundOrWithdrawRequestDTO fundOrWithdrawRequestDTO){
        return ResponseEntity.ok(this.transactionService.processTransaction(fundOrWithdrawRequestDTO));
    }


}
