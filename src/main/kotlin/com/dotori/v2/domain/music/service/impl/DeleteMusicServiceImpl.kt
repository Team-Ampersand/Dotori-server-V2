package com.dotori.v2.domain.music.service.impl

import com.dotori.v2.domain.member.enums.MusicStatus
import com.dotori.v2.domain.music.domain.entity.Music
import com.dotori.v2.domain.music.domain.repository.MusicLikeRepository
import com.dotori.v2.domain.music.domain.repository.MusicRepository
import com.dotori.v2.domain.music.exception.MusicNotFoundException
import com.dotori.v2.domain.music.presentation.data.res.MusicListResDto
import com.dotori.v2.domain.music.service.DeleteMusicService
import com.dotori.v2.global.config.redis.service.RedisCacheService
import com.dotori.v2.global.squirrel.MusicDotoriEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.core.env.Environment
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(rollbackFor = [Exception::class])
class DeleteMusicServiceImpl(
    private val musicRepository: MusicRepository,
    private val redisCacheService: RedisCacheService,
    private val musicLikeRepository: MusicLikeRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val env: Environment
) : DeleteMusicService {

    override fun execute(musicId: Long) {
        val music: Music = musicRepository.findByIdOrNull(musicId)
            ?: throw MusicNotFoundException()

        val date = music.createdDate.toLocalDate().toString()

        redisCacheService.putToCacheMusic(date, MusicListResDto(mutableListOf()))
        musicLikeRepository.deleteAllByMusicId(musicId)
        musicRepository.delete(music)
        music.member.updateMusicStatus(MusicStatus.CAN)
        applicationEventPublisher.publishEvent(
            MusicDotoriEvent.ofDeleteMusicEvent(
                "DOTORI ADMIN",
                createdAt = LocalDateTime.now(),
                env = env.activeProfiles[0],
                title = music.title
            )
        )
    }

}
