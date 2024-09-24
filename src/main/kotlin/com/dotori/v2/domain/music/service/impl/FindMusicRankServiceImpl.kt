package com.dotori.v2.domain.music.service.impl

import com.dotori.v2.domain.music.domain.entity.Music
import com.dotori.v2.domain.music.domain.repository.MusicLikeRepository
import com.dotori.v2.domain.music.domain.repository.MusicRepository
import com.dotori.v2.domain.music.presentation.data.res.MusicRankListResDto
import com.dotori.v2.domain.music.presentation.data.res.MusicRankResDto
import com.dotori.v2.domain.music.service.FindMusicRankService
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class FindMusicRankServiceImpl(
    private val musicRepository: MusicRepository,
    private val musicLikeRepository: MusicLikeRepository,
    private val userUtil: UserUtil
) : FindMusicRankService {

    override fun execute(date: LocalDate): MusicRankListResDto {
        val responses = musicRepository.findAllByCreatedDateOrderByLikeCountDESC(date)
            .mapIndexed { index, music ->
                MusicRankResDto(
                    id = music.id,
                    rank = index + 1,
                    url = music.url,
                    title = music.title,
                    thumbnail = music.thumbnail,
                    username = music.member.memberName,
                    email = music.member.email,
                    createdTime = music.createdDate,
                    stuNum = music.member.stuNum,
                    likeCount = music.likeCount,
                    memberLikeCheck = checkLike(music)
                )
            }

        return MusicRankListResDto(
            responses
        )
    }

    private fun checkLike(music: Music): Boolean {
        val member = userUtil.fetchCurrentUser()

        return musicLikeRepository.existsByMusicIdAndMemberId(music.id, member.id)
    }

}
