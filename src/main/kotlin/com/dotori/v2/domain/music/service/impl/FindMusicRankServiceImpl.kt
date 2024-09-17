package com.dotori.v2.domain.music.service.impl

import com.dotori.v2.domain.music.domain.repository.MusicRepository
import com.dotori.v2.domain.music.presentation.data.res.MusicRankListResDto
import com.dotori.v2.domain.music.presentation.data.res.MusicRankResDto
import com.dotori.v2.domain.music.service.FindMusicRankService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class FindMusicRankServiceImpl(
    private val musicRepository: MusicRepository
) : FindMusicRankService {

    override fun execute(date: LocalDate): MusicRankListResDto {
        val responses = musicRepository.findAllByCreatedDateOrderByLikeCount(date)
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
                    likeCount = music.likeCount
                )
            }

        return MusicRankListResDto(
            responses
        )
    }

}
