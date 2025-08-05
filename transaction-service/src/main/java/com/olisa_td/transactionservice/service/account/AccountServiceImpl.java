package com.olisa_td.transactionservice.service.account;

import com.olisa_td.transactionservice.dto.AccountResponse;
import com.olisa_td.transactionservice.exception.domain.AccountNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountOperations {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${account.service.url}")
    private String accountServiceUrl;


    private final WebClient.Builder webClientBuilder;

    public AccountServiceImpl (WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }



    @Override
    public Mono<AccountResponse> getAccount(String url,String id) {
        return webClientBuilder
                .baseUrl(accountServiceUrl)
                .build()
                .get()
                .uri(url, id)
                .retrieve()
                .bodyToMono(AccountResponse.class)
                .onErrorResume(WebClientResponseException.class,
                        error -> Mono.error(new AccountNotFoundException("Account not found")));
    }

}