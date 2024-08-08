package com.dotori.v2.domain.massage.util

import com.dotori.v2.domain.massage.exception.NotMassageApplyDayException
import com.dotori.v2.domain.massage.exception.NotMassageApplyHourException
import com.dotori.v2.domain.massage.exception.NotMassageCancelDayException
import com.dotori.v2.domain.massage.exception.NotMassageCancelHourException
import org.springframework.stereotype.Component
import java.lang.Exception
import java.time.DayOfWeek
import java.time.LocalDateTime

@Component
class ValidDayOfWeekAndHourMassageUtil {

    fun validateApply() {
        val currentTime = LocalDateTime.now()
        val dayOfWeek = currentTime.dayOfWeek
        val hour = currentTime.hour
        val minute = currentTime.minute
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
            throw NotMassageApplyDayException()
        if (hour == 20 && minute >= 20)
            return
        throw NotMassageApplyHourException()
    }

    fun validateCancel() {
        val currentTime = LocalDateTime.now()
        val dayOfWeek = currentTime.dayOfWeek
        val hour = currentTime.hour
        val minute = currentTime.minute
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
            throw NotMassageCancelDayException()
        if (hour == 20 && minute >= 20)
            return
        throw NotMassageCancelHourException()
    }

    fun isApplyValid(): Boolean {
        return try {
            validateApply()
            true
        } catch (e: Exception) {
            false
        }
    }

}