package com.dotori.v2.domain.music.service.impl

import com.dotori.v2.domain.member.enums.MusicStatus
import com.dotori.v2.domain.music.domain.entity.Music
import com.dotori.v2.domain.music.domain.repository.MusicRepository
import com.dotori.v2.domain.music.exception.MusicNotFoundException
import com.dotori.v2.domain.music.service.DeleteMusicService
import com.dotori.v2.global.config.redis.service.RedisCacheService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class DeleteMusicServiceImpl(
    private val musicRepository: MusicRepository,
    private val redisCacheService: RedisCacheService
) : DeleteMusicService {
    override fun execute(musicId: Long) {
        val music: Music = musicRepository.findByIdOrNull(musicId) ?: throw MusicNotFoundException()

        musicRepository.delete(music)
        redisCacheService.deleteFromCache("musicList:${music.createdDate.toLocalDate()}")
        music.member.updateMusicStatus(MusicStatus.CAN)
    }
}