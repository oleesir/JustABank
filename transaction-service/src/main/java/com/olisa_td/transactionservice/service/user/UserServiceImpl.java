package com.olisa_td.transactionservice.service.user;

import com.olisa_td.transactionservice.dto.UserResponse;
import com.olisa_td.transactionservice.exception.domain.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


@Service
public class UserServiceImpl implements UserOperations {

    @Autowired
    WebClient webClient;


    @Override
    public Mono<UserResponse> getUser(String url, String id) {
        return this.webClient
                .get()
                .uri(url, id)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .onErrorResume(WebClientResponseException.class,
                        error -> Mono.error(new UserNotFoundException("Account not found")));
    }
}
