package com.dotori.v2.domain.selfstudy.util

import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyApplyDayException
import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyApplyHourException
import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyCancelDayException
import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyCancelHourException
import com.dotori.v2.global.error.exception.BasicException
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.time.DayOfWeek
import java.time.LocalDateTime

@Component
class ValidDayOfWeekAndHourUtil(
    private val environment: Environment
) {

    fun validateApply() {
        val currentTime = LocalDateTime.now()
        val dayOfWeek = currentTime.dayOfWeek
        val hour = currentTime.hour
        val minute = currentTime.minute

        if (!isDevProfile() && (dayOfWeek == DayOfWeek.FRIDAY ||
                    dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)) {
            throw NotSelfStudyApplyDayException()
        }

        if(isDevProfile()) return

        if(hour == 20 && minute >= 0) return

        throw NotSelfStudyApplyHourException()
    }

    fun validateCancel() {
        val currentTime = LocalDateTime.now()
        val dayOfWeek = currentTime.dayOfWeek
        val hour = currentTime.hour
        val minute = currentTime.minute

        if (!isDevProfile() && (dayOfWeek == DayOfWeek.FRIDAY ||
                    dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)) {
            throw NotSelfStudyCancelDayException()
        }

        if(isDevProfile()) return

        if(hour == 20 && minute >= 0) return

        throw NotSelfStudyCancelHourException()
    }

    private fun isDevProfile(): Boolean {
        return environment.activeProfiles.contains("dev")
    }

    fun isApplyValid(): Boolean {
        return try {
            validateApply()
            true
        } catch (ex: BasicException) {
            false
        }
    }
}