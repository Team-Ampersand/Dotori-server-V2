package com.dotori.v2.domain.self_study.util

import com.dotori.v2.domain.self_study.excetpion.NotApplyDayException
import com.dotori.v2.domain.self_study.excetpion.NotApplyHourException
import org.springframework.stereotype.Component
import java.time.DayOfWeek

@Component
class ValidDayOfWeekAndHourUtil {
     fun validate(dayOfWeek: DayOfWeek, hour: Int) {
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
            throw NotApplyDayException()
        if (hour != 20)
            throw NotApplyHourException()
    }

}