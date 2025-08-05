package com.olisa_td.accountservice.dto;

public class UpdateStatusRequestDTO {

    String accountStatus;

    public UpdateStatusRequestDTO(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
}
