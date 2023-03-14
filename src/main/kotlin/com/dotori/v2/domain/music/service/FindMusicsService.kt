package com.dotori.v2.domain.music.service

import com.dotori.v2.domain.music.presentation.data.res.MusicListResDto
import java.time.LocalDate

interface FindMusicsService {
    fun execute(date: LocalDate): MusicListResDto
}