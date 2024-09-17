package com.dotori.v2.domain.music.service

import com.dotori.v2.domain.music.presentation.data.res.ToggleMusicResDto

interface ToggleMusicLikeService {
    fun execute(musicId: Long) : ToggleMusicResDto
}