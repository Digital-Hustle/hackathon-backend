package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // Минимальное количество потоков
        executor.setMaxPoolSize(20);  // Максимальное количество потоков
        executor.setQueueCapacity(50); // Размер очереди задач
        executor.initialize();
        return executor;
    }
}
