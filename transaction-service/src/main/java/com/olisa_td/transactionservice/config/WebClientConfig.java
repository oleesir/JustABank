package com.olisa_td.transactionservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;


    @Configuration
    public class WebClientConfig {


        @Bean
        public WebClient.Builder webClientBuilder() {
            return WebClient.builder().filter(addAuthToken());
        }


        private ExchangeFilterFunction addAuthToken() {
            return (req, next) -> {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String token = (authentication != null) ? (String) authentication.getCredentials() : null;

                if (token != null) {
                    return next.exchange(
                            ClientRequest.from(req)
                                    .header(HttpHeaders.AUTHORIZATION, token)
                                    .build()
                    );
                }
                return next.exchange(req);
            };
        }

    }
