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

        val like: Like? = likeRepository.findByMemberAndMusic(member, music)

        like?.let {
            deleteLike(like.id, music)
        }?: run {
            saveLike(music, member)
        }
    }

    private fun saveLike(music: Music, member: Member) {
        val like = Like (
            music = music,
            member = member
        )
        likeRepository.save(like)
        music.plusLikeCount()
    }

    private fun deleteLike (likeId: Long, music: Music) {
        likeRepository.deleteById(likeId)
        music.minusLikeCount()
    }

}