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
import com.dotori.v2.global.util.UserUtil
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional(readOnly = false, rollbackFor = [Exception::class])
class ToggleMusicLikeServiceImpl(
    private val musicLikeRepository: MusicLikeRepository,
    private val musicRepository: MusicRepository,
    private val userUtil: UserUtil,
    private val redisCacheService: RedisCacheService
): ToggleMusicLikeService {
    override fun execute(musicId: Long) : MusicLikeCountResDto {
        val member = userUtil.fetchCurrentUser()
        val music: Music = musicRepository.findByIdOrNull(musicId) ?: throw MusicNotFoundException()

        updateLike(music, member)

        return MusicLikeCountResDto (
            likeCount = music.likeCount
        )
    }

    private fun updateLike(music: Music, member: Member) {
        val like = musicLikeRepository.findByMemberIdAndMusicId(member.id, music.id)

        if (like == null) {
            saveLike(music, member)
        } else {
            deleteLike(like.id, music)
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
    }

    private fun deleteLike (likeId: Long, music: Music) {
        musicLikeRepository.deleteById(likeId)
        music.minusLikeCount()
        updateCache(music)
    }

    private fun updateCache(music: Music) {
        val cacheKey = music.createdDate.toLocalDate().toString()
        redisCacheService.deleteFromCacheMusic(cacheKey)
    }

}