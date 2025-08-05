package com.olisa_td.accountservice.security;

import com.olisa_td.accountservice.filter.AccountRequestFilter;
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
                       .requestMatchers("/accounts/**","/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .addFilterBefore(new AccountRequestFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
