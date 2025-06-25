package com.olisa_td.transactionservice.security;



import com.olisa_td.transactionservice.filter.CustomAuthEntryPoint;
import com.olisa_td.transactionservice.filter.TransactionRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableMethodSecurity
public class SecurityConfig {


    @Autowired
    CustomAuthEntryPoint customAuthEntryPoint;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(u -> u
                .requestMatchers("/**")
                .permitAll()
                .anyRequest().authenticated())
                .addFilterBefore(new TransactionRequestFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling((handler)-> handler.authenticationEntryPoint(this.customAuthEntryPoint));

        return http.build();
    }

}

