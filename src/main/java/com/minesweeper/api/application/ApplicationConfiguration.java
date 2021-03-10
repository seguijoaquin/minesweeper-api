package com.minesweeper.api.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.UUID;
import java.util.function.Supplier;

@Configuration
public class ApplicationConfiguration {
    @Bean
    @Primary
    public Supplier<UUID> uuidSupplier() {
        return UUID::randomUUID;
    }
}
