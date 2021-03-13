package com.minesweeper.api.adapter.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange().anyExchange().authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin().disable()
                .build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}password")
                .roles("USER")
                .build();
        UserDetails username = User.builder()
                .username("username")
                .password("{noop}password")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("another_username")
                .password("{noop}password")
                .roles("ADMIN")
                .build();
        UserDetails personal_username = User.builder()
                .username("personal_user")
                .password("{noop}password")
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user, username, admin, personal_username);
    }


}