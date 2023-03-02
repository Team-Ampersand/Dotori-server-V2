package com.dotori.v2.domain.board.presentation.data.res

import com.dotori.v2.domain.board.domain.entity.BoardImage
import com.dotori.v2.domain.member.enums.Role
import java.time.LocalDateTime

data class DetailBoardResDto(
    val id: Long,
    val title: String,
    val content: String,
    val roles: List<Role>,
    val boardImage: List<BoardImage>,
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime?
)
