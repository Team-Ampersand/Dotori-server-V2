package com.dotori.v2.domain.music.service.impl

import com.dotori.v2.domain.like.entity.Like
import com.dotori.v2.domain.like.repository.LikeRepository
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.music.domain.entity.Music
import com.dotori.v2.domain.music.domain.repository.MusicRepository
import com.dotori.v2.domain.music.exception.MusicNotFoundException
import com.dotori.v2.domain.music.service.ToggleMusicLikeService
import com.dotori.v2.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = false, rollbackFor = [Exception::class])
class ToggleMusicLikeServiceImpl(
    private val likeRepository: LikeRepository,
    private val musicRepository: MusicRepository,
    private val userUtil: UserUtil
): ToggleMusicLikeService {
    override fun execute(musicId: Long) {
        val member = userUtil.fetchCurrentUser()
        val music: Music = musicRepository.findByIdOrNull(musicId) ?: throw MusicNotFoundException()

        updateLike(music, member)
    }

    private fun updateLike(music: Music, member: Member) {
        val like = likeRepository.findByMemberIdAndMusicId(member.id, music.id)

        if (like == null) {
            saveLike(music, member)
        } else {
            deleteLike(like.id, music)
        }
    }

    private fun saveLike(music: Music, member: Member) {
        val like = Like (
            musicId = music.id,
            memberId = member.id
        )
        likeRepository.save(like)
        music.plusLikeCount()
    }

    private fun deleteLike (likeId: Long, music: Music) {
        likeRepository.deleteById(likeId)
        music.minusLikeCount()
    }

}