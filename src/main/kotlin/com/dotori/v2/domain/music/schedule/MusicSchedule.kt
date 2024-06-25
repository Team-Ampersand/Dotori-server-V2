package com.dotori.v2.domain.music.schedule

import com.dotori.v2.domain.music.domain.repository.MusicRepository
import com.dotori.v2.global.config.redis.service.RedisCacheService
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Component
@Transactional
class MusicSchedule(
    private val musicRepository: MusicRepository,
    private val redisTemplate: RedisTemplate<String, Any>
) {
    private val log = LoggerFactory.getLogger(this.javaClass.simpleName)

    @Scheduled(cron = "0 0 0 ? * MON-FRI")
    fun weekdayMusicStatusReset() {
        musicRepository.updateMusicStatusMemberByMember()
        val keys = redisTemplate.keys("musicList:*")
        if(keys.isNotEmpty()) {
            redisTemplate.delete(keys)
        }
        log.info("Student Music Status Updated At {}", LocalDate.now())
    }
}