package com.dotori.v2.domain.music.schedule

import com.dotori.v2.domain.music.domain.repository.MusicRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MusicSchedule(
    private val musicRepository: MusicRepository
) {
    @Scheduled(cron = "0 0 0 ? * MON-FRI")
    fun weekdayMusicStatusReset() {
        musicRepository.updateMusicStatusMemberByMember()
    }
}