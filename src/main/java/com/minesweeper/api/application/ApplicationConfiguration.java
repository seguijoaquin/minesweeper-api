package com.minesweeper.api.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;
import java.util.function.Supplier;

@Configuration
public class ApplicationConfiguration {
    @Bean
    @Primary
    public Supplier<UUID> uuidSupplier() {
        return UUID::randomUUID;
    }

    @Bean
    @Primary
    public Clock clock() {
        return Clock.systemUTC();
    }
}
