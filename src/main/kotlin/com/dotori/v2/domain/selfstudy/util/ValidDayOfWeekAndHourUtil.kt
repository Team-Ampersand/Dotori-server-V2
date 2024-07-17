package com.dotori.v2.domain.selfstudy.util

import com.dotori.v2.domain.selfstudy.properties.SelfStudyProperties
import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyApplyDayException
import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyApplyHourException
import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyCancelDayException
import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyCancelHourException
import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException
import org.springframework.stereotype.Component
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

@Component
class ValidDayOfWeekAndHourUtil(
    private val selfStudyProperties: SelfStudyProperties
) {

    fun validateApply() {
        val currentTime = LocalDateTime.now()
        val dayOfWeek = currentTime.dayOfWeek

        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
            throw NotSelfStudyApplyDayException()

        val allowedStartTime = selfStudyProperties.allowedStartTime?.let { LocalTime.parse(it) }
            ?: throw BasicException(ErrorCode.UNKNOWN_ERROR)
        val allowedEndTime = selfStudyProperties.allowedEndTime?.let { LocalTime.parse(it) }
            ?: throw BasicException(ErrorCode.UNKNOWN_ERROR)

        if (currentTime.toLocalTime().isBefore(allowedStartTime) || currentTime.toLocalTime().isAfter(allowedEndTime))
            throw NotSelfStudyApplyHourException()
    }

    fun validateCancel() {
        val currentTime = LocalDateTime.now()
        val dayOfWeek = currentTime.dayOfWeek

        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
            throw NotSelfStudyCancelDayException()

        val allowedStartTime = selfStudyProperties.allowedStartTime?.let { LocalTime.parse(it) }
            ?: throw BasicException(ErrorCode.UNKNOWN_ERROR)
        val allowedEndTime = selfStudyProperties.allowedEndTime?.let { LocalTime.parse(it) }
            ?: throw BasicException(ErrorCode.UNKNOWN_ERROR)

        if (currentTime.toLocalTime().isBefore(allowedStartTime) || currentTime.toLocalTime().isAfter(allowedEndTime))
            throw NotSelfStudyCancelHourException()
    }
}