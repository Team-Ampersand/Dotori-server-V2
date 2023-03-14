package com.dotori.v2.domain.music.service

import com.dotori.v2.domain.music.domain.entity.Music
import com.dotori.v2.domain.music.presentation.data.req.ApplyMusicReqDto
import java.time.DayOfWeek

interface ApplyMusicService {
    fun execute(applyMusicReqDto: ApplyMusicReqDto, dayOfWeek: DayOfWeek): Music
}