package com.dotori.v2.domain.music.presentation.data.res

import java.time.LocalDateTime

data class MusicRankResDto(
    val id: Long,
    val rank: Int,
    val url: String,
    val title: String,
    val thumbnail: String,
    val username: String,
    val email: String,
    val createdTime: LocalDateTime,
    val stuNum: String,
    val likeCount: Int,
    val memberLikeCheck: Boolean
)
