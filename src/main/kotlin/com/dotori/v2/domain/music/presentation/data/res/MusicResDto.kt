package com.dotori.v2.domain.music.presentation.data.res

import java.time.LocalDateTime

data class MusicResDto(
    val id: Long,
    val url: String,
    val title: String,
    val thumbnail: String,
    val username: String,
    val email: String,
    val createdTime: LocalDateTime,
    val stuNum: String
)