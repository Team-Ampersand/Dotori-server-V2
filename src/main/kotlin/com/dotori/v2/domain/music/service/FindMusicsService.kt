package com.dotori.v2.domain.music.service

import com.dotori.v2.domain.music.presentation.data.res.MusicListResDto
import java.time.LocalDateTime

interface FindMusicsService {
    fun execute(date: LocalDateTime): MusicListResDto
}