package com.dotori.v2.domain.massage.util

import com.dotori.v2.domain.massage.exception.NotMassageApplyDayException
import com.dotori.v2.domain.self_study.excetpion.NotSelfStudyApplyHourException
import com.dotori.v2.domain.self_study.excetpion.NotSelfStudyCancelDayException
import com.dotori.v2.domain.self_study.excetpion.NotSelfStudyCancelHourException
import org.springframework.stereotype.Component
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
        if (hour != 20 && minute < 30)
            throw NotSelfStudyApplyHourException()
    }

    fun validateCancel() {
        val currentTime = LocalDateTime.now()
        val dayOfWeek = currentTime.dayOfWeek
        val hour = currentTime.hour
        val minute = currentTime.minute
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
            throw NotSelfStudyCancelDayException()
        if (hour != 20 && minute < 30)
            throw NotSelfStudyCancelHourException()
    }

}