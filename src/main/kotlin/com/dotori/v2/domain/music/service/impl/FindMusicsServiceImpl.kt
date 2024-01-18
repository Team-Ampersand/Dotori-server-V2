package com.dotori.v2.domain.music.service.impl

import com.dotori.v2.domain.music.domain.entity.Music
import com.dotori.v2.domain.music.domain.repository.MusicRepository
import com.dotori.v2.domain.music.presentation.data.res.MusicListResDto
import com.dotori.v2.domain.music.presentation.data.res.MusicResDto
import com.dotori.v2.domain.music.service.FindMusicsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class FindMusicsServiceImpl(
    private val musicRepository: MusicRepository
) : FindMusicsService {
    override fun execute(date: LocalDate): MusicListResDto {
        return MusicListResDto(
            content = musicRepository.findAllByCreatedDate(date)
                .map { toDto(it) }
        )
    }

    private fun toDto(music: Music): MusicResDto =
        MusicResDto(
            id = music.id,
            url = music.url,
            title = music.title,
            thumbnail = music.thumbnail,
            username = music.member.memberName,
            email = music.member.email,
            createdTime = music.createdDate,
            stuNum = music.member.stuNum
        )
}