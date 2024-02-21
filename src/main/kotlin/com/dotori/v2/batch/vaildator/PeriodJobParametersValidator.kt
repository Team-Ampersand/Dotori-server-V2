package com.dotori.v2.batch.vaildator

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersInvalidException
import org.springframework.batch.core.JobParametersValidator

class PeriodJobParametersValidator : JobParametersValidator {

    @Throws(JobParametersInvalidException::class)
    override fun validate(parameters: JobParameters?) {
        val n = parameters?.getString("period")

        // n 값이 null이거나 "n기" 형식이 아닌 경우 예외를 던집니다.
        if (n == null || !n.matches(Regex("\\d+기"))) {
            throw JobParametersInvalidException("Parameter 'n' must be in the format 'n기'.")
        }
    }
}