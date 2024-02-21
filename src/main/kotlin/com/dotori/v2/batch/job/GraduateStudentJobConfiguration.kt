package com.dotori.v2.batch.job

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.context.annotation.Configuration

@Configuration
class GraduateStudentJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    companion object {
        const val JOB_NAME = "graduateJob"
        const val CHUNK_SIZE = 50
    }
}