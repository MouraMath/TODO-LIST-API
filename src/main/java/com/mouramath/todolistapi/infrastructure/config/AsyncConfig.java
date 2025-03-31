package com.mouramath.todolistapi.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig {
    // A anotação @EnableAsync é suficiente para habilitar o processamento assíncrono
}