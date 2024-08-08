package com.dotori.v2.domain.selfstudy.util

import com.dotori.v2.domain.selfstudy.properties.SelfStudyProperties
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
    private val selfStudyProperties: SelfStudyProperties,
    private val environment: Environment
) {

    fun validateApply() {
        val currentTime = LocalDateTime.now()
        val dayOfWeek = currentTime.dayOfWeek
        val hour = currentTime.hour
        val minute = currentTime.minute

        if (!isDevProfile() && (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)) {
            throw NotSelfStudyApplyDayException()
        }

        val allowedStartTime = selfStudyProperties.allowedStartTime.split(":").map { it.toInt() }.toIntArray()
        val allowedEndTime = selfStudyProperties.allowedEndTime.split(":").map { it.toInt() }.toIntArray()

        // 시간은 start hour: start minute ~ end 사이이면서
        if (hour >= allowedStartTime[0] && minute >= allowedStartTime[1] && hour <= allowedEndTime[0] && minute <= allowedEndTime[1])
            return

        throw NotSelfStudyApplyHourException()
    }

    fun validateCancel() {
        val currentTime = LocalDateTime.now()
        val dayOfWeek = currentTime.dayOfWeek
        val hour = currentTime.hour
        val minute = currentTime.minute

        if (!isDevProfile() && (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)) {
            throw NotSelfStudyCancelDayException()
        }

        val allowedStartTime = selfStudyProperties.allowedStartTime.split(":").map { it.toInt() }.toIntArray()
        val allowedEndTime = selfStudyProperties.allowedEndTime.split(":").map { it.toInt() }.toIntArray()

        if (hour >= allowedStartTime[0] && minute >= allowedStartTime[1] && hour <= allowedEndTime[0] && minute <= allowedEndTime[1])
            return

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