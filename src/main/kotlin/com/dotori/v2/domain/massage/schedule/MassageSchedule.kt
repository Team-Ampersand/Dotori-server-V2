package com.dotori.v2.domain.massage.schedule

import com.dotori.v2.domain.massage.service.UpdateMassageStatusService
import com.dotori.v2.global.webhook.client.DiscordClient
import org.springframework.core.env.Environment
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class MassageSchedule(
    private val updateMassageStatusService: UpdateMassageStatusService,
    private val discordClient: DiscordClient,
    private val env: Environment
) {
    @Scheduled(cron = "0 0 2 ? * MON-FRI")
    fun weekdaySelfStudyStatusReset(){
        updateMassageStatusService.execute()

        val profile = env.activeProfiles.first()
        if(profile == "prod"){
            discordClient.sendMessage("""
                DOTORI - 안마의자 신청 정보, 상태 초기화 작업 완료.
                time - ${LocalDateTime.now()}
            """.trimIndent()
            )
        }
    }
}