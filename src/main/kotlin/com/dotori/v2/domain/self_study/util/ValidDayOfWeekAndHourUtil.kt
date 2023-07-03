package com.dotori.v2.domain.self_study.util

import com.dotori.v2.domain.self_study.exception.NotSelfStudyApplyDayException
import com.dotori.v2.domain.self_study.exception.NotSelfStudyApplyHourException
import com.dotori.v2.domain.self_study.exception.NotSelfStudyCancelDayException
import com.dotori.v2.domain.self_study.exception.NotSelfStudyCancelHourException
import org.springframework.stereotype.Component
import java.time.DayOfWeek
import java.time.LocalDateTime

@Component
class ValidDayOfWeekAndHourUtil(
    private val currentTime: LocalDateTime? = null
) {
     fun validateApply() {
         val currentTime = currentTime ?: LocalDateTime.now()
         val dayOfWeek = currentTime.dayOfWeek
         val hour = currentTime.hour

         if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY)
            throw NotSelfStudyApplyDayException()

         if (hour != 20)
            throw NotSelfStudyApplyHourException()
    }

    fun validateCancel() {
        val currentTime = currentTime ?: LocalDateTime.now()
        val dayOfWeek = currentTime.dayOfWeek
        val hour = currentTime.hour

        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY)
            throw NotSelfStudyCancelDayException()

        if (hour != 20)
            throw NotSelfStudyCancelHourException()
    }

}