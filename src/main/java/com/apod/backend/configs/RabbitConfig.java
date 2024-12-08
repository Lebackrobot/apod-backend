package com.apod.backend.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    Queue queue() {
        String queue = "tokens";
        return new Queue(queue, true);
    }
}
