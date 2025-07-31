package com.olisa_td.transactionservice.controller;

import com.olisa_td.transactionservice.domain.PageResponse;
import com.olisa_td.transactionservice.dto.TransferTrxRequestDTO;
import com.olisa_td.transactionservice.dto.DepositOrWithdrawTrxRequestDTO;
import com.olisa_td.transactionservice.dto.TrxResponseDTO;
import com.olisa_td.transactionservice.jpa.Transaction;
import com.olisa_td.transactionservice.service.transaction.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/deposit_or_withdraw")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<TrxResponseDTO> createTransaction(@Valid @RequestBody DepositOrWithdrawTrxRequestDTO depositOrWithdrawTrxRequestDTO){
        return ResponseEntity.ok(this.transactionService.processDepositOrWithdrawTrx(depositOrWithdrawTrxRequestDTO));
    }

    @PostMapping("/transfer")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<TrxResponseDTO>processTransaction(@Valid @RequestBody TransferTrxRequestDTO transferTrxRequestDTO){
        return ResponseEntity.ok(this.transactionService.processTransferTrx(transferTrxRequestDTO));
    }



    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_STAFF', 'ROLE_ADMIN')")
    public ResponseEntity<PageResponse<Transaction>> getAllTransactions(@RequestParam int pageNum, @RequestParam int pageSize) {

        return ResponseEntity.ok(this.transactionService.getAllTransactions(pageNum, pageSize));
    }


    @GetMapping("/my_trx")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<PageResponse<Transaction>> getAllTransactionsByUserId(@RequestParam int pageNum, @RequestParam int pageSize) {

        return ResponseEntity.ok(this.transactionService.getAllTransactionsByUserId(pageNum, pageSize));
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_STAFF', 'ROLE_ADMIN')")
    public ResponseEntity<Transaction> getTransaction(@PathVariable String id){
        return ResponseEntity.ok(this.transactionService.getTransaction(id));
    }

}
