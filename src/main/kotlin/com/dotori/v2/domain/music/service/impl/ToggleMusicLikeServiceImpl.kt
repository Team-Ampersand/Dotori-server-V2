package com.dotori.v2.domain.music.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.music.domain.entity.Music
import com.dotori.v2.domain.music.domain.entity.MusicLike
import com.dotori.v2.domain.music.domain.repository.MusicLikeRepository
import com.dotori.v2.domain.music.domain.repository.MusicRepository
import com.dotori.v2.domain.music.exception.MusicNotFoundException
import com.dotori.v2.domain.music.presentation.data.res.MusicLikeCountResDto
import com.dotori.v2.domain.music.service.ToggleMusicLikeService
import com.dotori.v2.global.config.redis.service.RedisCacheService
import com.dotori.v2.global.squirrel.MusicDotoriEvent
import com.dotori.v2.global.util.UserUtil
import org.springframework.context.ApplicationEventPublisher
import org.springframework.core.env.Environment
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional(readOnly = false, rollbackFor = [Exception::class])
class ToggleMusicLikeServiceImpl(
    private val musicLikeRepository: MusicLikeRepository,
    private val musicRepository: MusicRepository,
    private val userUtil: UserUtil,
    private val redisCacheService: RedisCacheService,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val env: Environment
): ToggleMusicLikeService {

    override fun execute(musicId: Long) : MusicLikeCountResDto {
        val member = userUtil.fetchCurrentUser()
        val music: Music = musicRepository.findByIdForUpdate(musicId) ?: throw MusicNotFoundException()

        updateLike(music, member)

        return MusicLikeCountResDto(
            likeCount = music.likeCount
        )
    }

    private fun updateLike(music: Music, member: Member) {
        val like = musicLikeRepository.findByMemberIdAndMusicId(member.id, music.id)

        if (like == null) {
            saveLike(music, member)
        } else {
            deleteLike(like.id, music, member.memberName)
        }
    }

    private fun saveLike(music: Music, member: Member) {
        val musicLike = MusicLike (
            musicId = music.id,
            memberId = member.id
        )
        musicLikeRepository.save(musicLike)
        music.plusLikeCount()
        updateCache(music)
        applicationEventPublisher.publishEvent(
            MusicDotoriEvent.ofCreateMusicLikeEvent(
                member.memberName,
                createdAt = LocalDateTime.now(),
                env = env.activeProfiles[0],
                title = music.title
            )
        )
    }

    private fun deleteLike (likeId: Long, music: Music, memberName: String) {
        musicLikeRepository.deleteById(likeId)
        music.minusLikeCount()
        updateCache(music)
        applicationEventPublisher.publishEvent(
            MusicDotoriEvent.ofDeleteMusicLikeEvent(
                memberName,
                createdAt = LocalDateTime.now(),
                env = env.activeProfiles[0],
                title = music.title
            )
        )
    }

    private fun updateCache(music: Music) {
        val cacheKey = music.createdDate.toLocalDate().toString()
        redisCacheService.deleteFromCacheMusic(cacheKey)
    }

}
