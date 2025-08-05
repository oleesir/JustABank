package com.olisa_td.gatewayservice.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;


@RestControllerAdvice
public class ExceptionHandling {
    final Logger logger = LoggerFactory.getLogger(this.getClass());


    @ExceptionHandler(WebClientResponseException.Unauthorized.class)
    public Mono<Void> handleUnauthorizedException(ServerWebExchange exchange) {

        String body = """
    {
        "httpStatusCode": 401,
        "httpStatus": "UNAUTHORIZED",
        "message": "You need to log in to access this URL.",
    }
    """;

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
       exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);

        return exchange.getResponse()
                .writeWith(Mono.just(exchange.getResponse()
                        .bufferFactory()
                        .wrap(bytes)));
    }

}