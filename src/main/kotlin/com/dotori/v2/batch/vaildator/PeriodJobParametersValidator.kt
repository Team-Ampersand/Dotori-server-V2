package com.dotori.v2.batch.vaildator

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersInvalidException
import org.springframework.batch.core.JobParametersValidator

@Deprecated("validator is deprecated")
class PeriodJobParametersValidator : JobParametersValidator {

    @Throws(JobParametersInvalidException::class)
    override fun validate(parameters: JobParameters?) {
        val period = parameters?.getString("period")

        // n 값이 null이거나 "n기" 형식이 아닌 경우 예외를 던집니다.
        if (period == null || !period.matches(Regex("\\d+기"))) {
            throw JobParametersInvalidException("Parameter 'n' must be in the format 'n기'. param.period = $period")
        }
    }
}