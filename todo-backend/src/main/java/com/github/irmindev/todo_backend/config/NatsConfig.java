package com.github.irmindev.todo_backend.config;

import io.nats.client.Connection;
import io.nats.client.Nats;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class NatsConfig {
    @Value("${nats.server}")
    private String natsUrl;

    @Bean
    public Connection natsConnection() throws IOException, InterruptedException, TimeoutException {
        return Nats.connect(natsUrl);
    }
}
