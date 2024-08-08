package com.dotori.v2.domain.selfstudy.util

import com.dotori.v2.domain.massage.exception.NotMassageCancelDayException
import com.dotori.v2.domain.massage.exception.NotMassageCancelHourException
import com.dotori.v2.domain.selfstudy.properties.SelfStudyProperties
import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyApplyDayException
import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyApplyHourException
import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyCancelDayException
import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyCancelHourException
import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZonedDateTime

@Component
class ValidDayOfWeekAndHourUtil(
    private val selfStudyProperties: SelfStudyProperties,
) {

    fun validateApply() {
        val currentTime = LocalDateTime.now()
        val dayOfWeek = currentTime.dayOfWeek
        val hour = currentTime.hour
        val minute = currentTime.minute

        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw NotSelfStudyApplyDayException()
        }

        val allowedStartTime = selfStudyProperties.allowedStartTime.split(":").map { it.toInt() }.toIntArray()
        val allowedEndTime = selfStudyProperties.allowedEndTime.split(":").map { it.toInt() }.toIntArray()

        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
            throw NotSelfStudyApplyHourException()

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

        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw NotSelfStudyApplyDayException()
        }

        val allowedStartTime = selfStudyProperties.allowedStartTime.split(":").map { it.toInt() }.toIntArray()
        val allowedEndTime = selfStudyProperties.allowedEndTime.split(":").map { it.toInt() }.toIntArray()

        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
            throw NotSelfStudyApplyHourException()

        // 시간은 start hour: start minute ~ end 사이이면서
        if (hour >= allowedStartTime[0] && minute >= allowedStartTime[1] && hour <= allowedEndTime[0] && minute <= allowedEndTime[1])
            return

        throw NotSelfStudyApplyHourException()
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