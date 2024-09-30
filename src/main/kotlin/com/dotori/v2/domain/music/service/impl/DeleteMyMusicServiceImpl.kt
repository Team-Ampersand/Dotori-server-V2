package com.dotori.v2.domain.music.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.MusicStatus
import com.dotori.v2.domain.music.domain.entity.Music
import com.dotori.v2.domain.music.domain.repository.MusicLikeRepository
import com.dotori.v2.domain.music.domain.repository.MusicRepository
import com.dotori.v2.domain.music.exception.MusicNotFoundException
import com.dotori.v2.domain.music.exception.NotMyMusicException
import com.dotori.v2.domain.music.presentation.data.res.MusicListResDto
import com.dotori.v2.domain.music.service.DeleteMyMusicService
import com.dotori.v2.global.config.redis.service.RedisCacheService
import com.dotori.v2.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class DeleteMyMusicServiceImpl(
    private val musicRepository: MusicRepository,
    private val userUtil: UserUtil,
    private val redisCacheService: RedisCacheService,
    private val musicLikeRepository: MusicLikeRepository
) : DeleteMyMusicService {


    override fun execute(musicId: Long) {
        val music: Music = musicRepository.findByIdOrNull(musicId)
            ?: throw MusicNotFoundException()

        val member: Member = userUtil.fetchCurrentUser()

        validMusic(music, member)

        val date = music.createdDate.toLocalDate().toString()

        redisCacheService.putToCacheMusic(date, MusicListResDto(mutableListOf()))
        musicLikeRepository.deleteAllByMusicId(musicId)
        musicRepository.delete(music)
        music.member.updateMusicStatus(MusicStatus.CAN)
    }

    private fun validMusic(music: Music, member: Member) {
        if (music.member.id != member.id) {
            throw NotMyMusicException()
        }
    }
}