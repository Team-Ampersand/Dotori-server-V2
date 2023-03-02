package com.dotori.v2.domain.music.schedule

import com.dotori.v2.domain.music.domain.repository.MusicRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Component
@Transactional
class MusicSchedule(
    private val musicRepository: MusicRepository
) {
    private val log = LoggerFactory.getLogger(this.javaClass.simpleName)

    @Scheduled(cron = "0 0 0 ? * MON-FRI")
    fun weekdayMusicStatusReset() {
        musicRepository.updateMusicStatusMemberByMember()
        log.info("Student Music Status Updated At {}", LocalDate.now())
    }
}