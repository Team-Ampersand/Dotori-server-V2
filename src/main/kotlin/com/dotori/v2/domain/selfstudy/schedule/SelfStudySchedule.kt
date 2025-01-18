package com.dotori.v2.domain.selfstudy.schedule

import com.dotori.v2.domain.selfstudy.service.UpdateSelfStudyStatusService
import com.dotori.v2.global.webhook.client.DiscordClient
import org.springframework.core.env.Environment
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class SelfStudySchedule(
    private val updateSelfStudyStatusService: UpdateSelfStudyStatusService,
    private val discordClient: DiscordClient,
    private val env: Environment
) {
    @Scheduled(cron = "0 0 2 ? * MON-FRI")
    fun weekdaySelfStudyStatusReset(){
        updateSelfStudyStatusService.execute()
        val profile = env.activeProfiles.first()

        if(profile == "prod"){
            discordClient.sendMessage("""
                DOTORI - 자습 신청 정보, 상태 초기화 작업 완료.
                time - ${LocalDateTime.now()}
            """.trimIndent()
            )
        }
    }
}