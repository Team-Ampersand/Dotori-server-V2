package com.dotori.v2.domain.music.service

import com.dotori.v2.domain.music.presentation.data.res.MusicLikeCountResDto

interface ToggleMusicLikeService {
    fun execute(musicId: Long) : MusicLikeCountResDto
}