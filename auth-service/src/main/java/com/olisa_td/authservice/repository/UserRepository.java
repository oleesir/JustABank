package com.olisa_td.authservice.repository;

import com.olisa_td.authservice.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, PagingAndSortingRepository<User,UUID> {
    Optional<User> findByEmail(String email);

}