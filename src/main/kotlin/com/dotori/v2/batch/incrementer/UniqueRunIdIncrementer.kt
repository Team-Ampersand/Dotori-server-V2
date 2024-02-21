package com.dotori.v2.batch.incrementer

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer

@Deprecated("validator is deprecated")
class UniqueRunIdIncrementer : RunIdIncrementer() {

    companion object {
        const val RUN_ID = "run.id"
    }

    override fun getNext(parameters: JobParameters?): JobParameters {
        val param = parameters ?: JobParameters()
        return JobParametersBuilder()
            .addLong(RUN_ID, param.getLong(RUN_ID, 0L) + 1)
            .toJobParameters()
    }
}