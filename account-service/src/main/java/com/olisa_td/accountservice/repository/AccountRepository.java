package com.olisa_td.accountservice.repository;

import com.olisa_td.accountservice.jpa.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

}
