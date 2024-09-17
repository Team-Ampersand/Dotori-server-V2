package com.dotori.v2.domain.music.service

import com.dotori.v2.domain.music.presentation.data.res.MusicRankListResDto
import java.time.LocalDate

interface FindMusicRankService {
    fun execute(date: LocalDate): MusicRankListResDto
}