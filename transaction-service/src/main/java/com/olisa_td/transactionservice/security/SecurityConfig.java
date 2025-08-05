package com.olisa_td.transactionservice.security;



import com.olisa_td.transactionservice.filter.TransactionRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableMethodSecurity
public class SecurityConfig {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(u -> u
                        .requestMatchers("/transactions/**","/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .addFilterBefore(new TransactionRequestFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}

