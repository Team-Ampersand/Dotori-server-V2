package com.dotori.v2.batch.config

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BatchBuilderFactoryConfig : DefaultBatchConfigurer() {

    @Bean
    fun jobBuilderFactory(): JobBuilderFactory = JobBuilderFactory(jobRepository)


    @Bean
    fun stepBuilderFactory(): StepBuilderFactory = StepBuilderFactory(jobRepository, transactionManager)

}