package com.olisa_td.transactionservice.service.account;

import com.olisa_td.transactionservice.dto.AccountResponse;
import com.olisa_td.transactionservice.exception.domain.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountOperations {

    @Autowired
    WebClient webClient;


    @Override
    public Mono<AccountResponse> getAccount(String url,String id) {
        return this.webClient
                .get()
                .uri(url, id)
                .retrieve()
                .bodyToMono(AccountResponse.class)
                .onErrorResume(WebClientResponseException.class,
                        error -> Mono.error(new AccountNotFoundException("Account not found")));
    }

}