package com.dotori.v2.domain.massage.schedule

import com.dotori.v2.domain.massage.service.UpdateMassageStatusService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MassageSchedule(
    private val updateMassageStatusService: UpdateMassageStatusService
) {
    @Scheduled(cron = "0 0 2 ? * MON-FRI")
    fun weekdaySelfStudyStatusReset(){
        updateMassageStatusService.execute()
    }
}