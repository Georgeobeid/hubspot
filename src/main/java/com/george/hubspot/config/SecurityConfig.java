package com.george.hubspot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // (opcional) para facilitar testes de POST
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll() // libera todas as rotas
                )
                .formLogin().disable(); // desabilita o form login padrão
        return http.build();
    }
}