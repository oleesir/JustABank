package com.olisa_td.transactionservice.service.user;

import com.olisa_td.transactionservice.dto.UserResponse;
import com.olisa_td.transactionservice.exception.domain.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


@Service
public class UserServiceImpl implements UserOperations {

        @Value("${auth.service.url}")
        private String authServiceUrl;

    private final WebClient.Builder webClientBuilder;

    public UserServiceImpl (WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }


    @Override
    public Mono<UserResponse> getUser(String url, String id) {
        return webClientBuilder
                .baseUrl(authServiceUrl)
                .build()
                .get()
                .uri(url, id)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .onErrorResume(WebClientResponseException.class,
                        error -> Mono.error(new UserNotFoundException("User not found")));
    }
}
