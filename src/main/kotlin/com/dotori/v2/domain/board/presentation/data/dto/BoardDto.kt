package com.dotori.v2.domain.board.presentation.data.dto

import com.dotori.v2.domain.member.enums.Role
import java.time.LocalDate
import java.time.LocalDateTime

data class BoardDto(
    val id: Long,
    val title: String,
    val roles: List<Role>,
    val boardImages: List<BoardImageDto>,
    val createdDate: LocalDateTime
)