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
    private val selfStudyProperties: SelfStudyProperties,
    private val currentTime: LocalDateTime? = null
) {

     fun validateApply() {
         val currentTime = currentTime ?: LocalDateTime.now()
         val dayOfWeek = currentTime.dayOfWeek
         val hour = currentTime.hour

         if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
            throw NotSelfStudyApplyDayException()

         val allowedTime = selfStudyProperties.allowedTime ?: throw BasicException(ErrorCode.BAD_REQUEST);
         val allowedStartTime = LocalTime.parse(allowedTime.split("-")[0])
         val allowedEndTime = LocalTime.parse(allowedTime.split("-")[1])

         if (currentTime.toLocalTime().isBefore(allowedStartTime) || currentTime.toLocalTime().isAfter(allowedEndTime))
             throw NotSelfStudyApplyHourException()
    }

    fun validateCancel() {
        val currentTime = currentTime ?: LocalDateTime.now()
        val dayOfWeek = currentTime.dayOfWeek
        val hour = currentTime.hour

        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
            throw NotSelfStudyCancelDayException()

        val allowedTime = selfStudyProperties.allowedTime ?: throw BasicException(ErrorCode.BAD_REQUEST);
        val allowedStartTime = LocalTime.parse(allowedTime.split("-")[0])
        val allowedEndTime = LocalTime.parse(allowedTime.split("-")[1])

        if (currentTime.toLocalTime().isBefore(allowedStartTime) || currentTime.toLocalTime().isAfter(allowedEndTime))
            throw NotSelfStudyCancelHourException()
    }
}