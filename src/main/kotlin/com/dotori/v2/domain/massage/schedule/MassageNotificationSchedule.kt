package com.dotori.v2.domain.massage.schedule

import com.dotori.v2.global.webhook.client.DiscordClient
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class MassageNotificationSchedule(
    private val discordClient: DiscordClient
) {

    @Scheduled(cron = "0 0 20 ? * MON-THU")
    fun notifyMassageOpen() {
        discordClient.sendMessage("""
            DOTORI - 안마 의자 신청이 오픈되었습니다.
            time - ${LocalDateTime.now()}
        """.trimIndent())
    }
}