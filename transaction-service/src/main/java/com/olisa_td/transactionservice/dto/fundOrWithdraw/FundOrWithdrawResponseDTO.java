package com.olisa_td.transactionservice.dto.fundOrWithdraw;



import com.olisa_td.transactionservice.jpa.TransactionType;

import java.math.BigDecimal;

public class FundOrWithdrawResponseDTO {
    private Long accountNumber;
    private BigDecimal amount;
    private TransactionType transactionType;


    public FundOrWithdrawResponseDTO() {

    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

}
