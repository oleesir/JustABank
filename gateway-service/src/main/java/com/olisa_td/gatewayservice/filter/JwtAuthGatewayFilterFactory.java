package com.olisa_td.gatewayservice.filter;


import com.olisa_td.gatewayservice.utils.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class JwtAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final WebClient webClient;

    public JwtAuthGatewayFilterFactory(WebClient.Builder webClientBuilder,@Value("${auth.service.url}") String authServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
    }

    @Override
    public GatewayFilter apply(Object config){
        return(exchange,chain)->{
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if(token == null || !token.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }


            System.out.println("TOKEN " + token);

            return webClient.get()
                    .uri("/validate")
                    .header(HttpHeaders.AUTHORIZATION,token)
                    .retrieve()
                    .bodyToMono(TokenResponse.class)
                    .doOnNext(details -> {
                        System.out.println("TOKEN DETAILS (via doOnNext): " + details);
                    }).flatMap(details -> {
                        System.out.println("TOKEN DETAILS " + details);

                        exchange.getRequest().mutate()
                                .header("x-userId", details.getId())
                                .header("x-role",  details.getRole())
                                .build();
                        return chain.filter(exchange);
                    });
        };

    }

}
