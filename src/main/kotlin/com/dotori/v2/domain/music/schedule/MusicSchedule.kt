package com.dotori.v2.domain.music.schedule

import com.dotori.v2.domain.music.domain.repository.MusicLikeRepository
import com.dotori.v2.domain.music.domain.repository.MusicRepository
import com.dotori.v2.global.config.redis.service.RedisCacheService
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Component
@Transactional
class MusicSchedule(
    private val musicRepository: MusicRepository,
    private val musicLikeRepository: MusicLikeRepository,
    private val redisCacheService: RedisCacheService
) {
    private val log = LoggerFactory.getLogger(this.javaClass.simpleName)

    @Scheduled(cron = "0 0 0 ? * MON-FRI")
    fun weekdayMusicStatusReset() {
        musicRepository.updateMusicStatusMemberByMember()
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    fun initMusic() {
        val localDateTime = LocalDateTime.now().minus(2, ChronoUnit.MONTHS)
        val musics = musicRepository.findAllByCreatedDateBefore(localDateTime)
        musicLikeRepository.deleteAllByMusicIdIn(musics.map { it.id })
        musicRepository.deleteAllInBatch(musics)
        redisCacheService.initCache("musicList:*")
        log.info("delete music data schedule")
    }

}