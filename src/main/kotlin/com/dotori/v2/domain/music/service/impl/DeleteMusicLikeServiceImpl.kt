package com.dotori.v2.domain.music.service.impl

import com.dotori.v2.domain.music.domain.entity.Music
import com.dotori.v2.domain.like.repository.LikeRepository
import com.dotori.v2.domain.music.domain.repository.MusicRepository
import com.dotori.v2.domain.music.exception.MusicNotFoundException
import com.dotori.v2.domain.music.service.DeleteMusicLikeService
import com.dotori.v2.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = false, rollbackFor = [Exception::class])
class DeleteMusicLikeServiceImpl (
    private val likeRepository: LikeRepository,
    private val userUtil: UserUtil,
    private val musicRepository: MusicRepository
): DeleteMusicLikeService {
    override fun execute(musicId: Long) {
        val member = userUtil.fetchCurrentUser()
        val music: Music = musicRepository.findByIdOrNull(musicId) ?: throw MusicNotFoundException()

        val like = likeRepository.findByMemberAndMusic(member, music)
        likeRepository.deleteById(like.id)

        music.minusLikeCount()
    }
}