package com.dotori.v2.domain.self_study.schedule

import com.dotori.v2.domain.self_study.service.UpdateSelfStudyStatusService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SelfStudySchedule(
    private val updateSelfStudyStatusService: UpdateSelfStudyStatusService,
) {
    @Scheduled(cron = "0 0 2 ? * MON-FRI")
    fun weekdaySelfStudyStatusReset(){
        updateSelfStudyStatusService.execute()
    }
}