package com.dotori.v2.global.async

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor


@EnableAsync
@Configuration
class AsyncConfig {

    @Bean
    fun squirrelTaskExecutor(): ThreadPoolTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 5
        executor.maxPoolSize = 10
        executor.setQueueCapacity(25)
        executor.setThreadNamePrefix("Async-")
        executor.initialize()
        return executor
    }
}