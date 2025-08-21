package com.giatrong.learning.learnspringapi.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MetricsConfig {

    @Bean
    public Counter userCreationCounter(MeterRegistry meterRegistry) {
        return Counter.builder("user.creation.count")
                .description("Number of users created")
                .register(meterRegistry);
    }

    @Bean
    public Timer userFetchTimer(MeterRegistry meterRegistry) {
        return Timer.builder("user.fetch.time")
                .description("Time spent fetching users")
                .register(meterRegistry);
    }
}
