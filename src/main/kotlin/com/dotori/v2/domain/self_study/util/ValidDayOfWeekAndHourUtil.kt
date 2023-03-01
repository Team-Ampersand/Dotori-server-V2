package com.dotori.v2.domain.self_study.util

import com.dotori.v2.domain.self_study.excetpion.NotApplyDayException
import com.dotori.v2.domain.self_study.excetpion.NotApplyHourException
import com.dotori.v2.domain.self_study.excetpion.NotCancelDayException
import com.dotori.v2.domain.self_study.excetpion.NotCancelHourException
import org.springframework.stereotype.Component
import java.time.DayOfWeek
import java.time.LocalDateTime

@Component
class ValidDayOfWeekAndHourUtil {
     fun validateApply() {
         val currentTime = LocalDateTime.now()
         val dayOfWeek = currentTime.dayOfWeek
         val hour = currentTime.hour
         if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
            throw NotApplyDayException()
        if (hour != 20)
            throw NotApplyHourException()
    }

    fun validateCancel() {
        val currentTime = LocalDateTime.now()
        val dayOfWeek = currentTime.dayOfWeek
        val hour = currentTime.hour
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
            throw NotCancelDayException()
        if (hour != 20)
            throw NotCancelHourException()
    }

}