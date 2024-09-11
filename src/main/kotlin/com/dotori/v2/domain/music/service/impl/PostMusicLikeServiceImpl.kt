package com.dotori.v2.domain.music.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.like.entity.Like
import com.dotori.v2.domain.music.domain.entity.Music
import com.dotori.v2.domain.like.repository.LikeRepository
import com.dotori.v2.domain.music.domain.repository.MusicRepository
import com.dotori.v2.domain.music.exception.MusicNotFoundException
import com.dotori.v2.domain.music.exception.NotValidMusicLikeException
import com.dotori.v2.domain.music.service.PostMusicLikeService
import com.dotori.v2.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional(readOnly = false, rollbackFor = [Exception::class])
class PostMusicLikeServiceImpl (
    private val likeRepository: LikeRepository,
    private val musicRepository: MusicRepository,
    private val userUtil: UserUtil
): PostMusicLikeService {
    override fun execute(musicId: Long) {
        val member = userUtil.fetchCurrentUser()
        val music: Music = musicRepository.findByIdOrNull(musicId) ?: throw MusicNotFoundException()
        val date = LocalDateTime.now()

        require(music.createdDate.dayOfWeek == date.dayOfWeek) {throw NotValidMusicLikeException()}

        saveLike(member, music)
    }

    private fun saveLike(member: Member, music: Music) {
        val like = Like (
            music = music,
            member = member
        )
        music.likeCount += 1
        musicRepository.save(music)

        likeRepository.save(like)
    }
}