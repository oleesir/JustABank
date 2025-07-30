package com.olisa_td.accountservice.repository;

import com.olisa_td.accountservice.jpa.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findAllByUserId(String id);
//    Page<Account>findAll(Pageable pageable);

}
