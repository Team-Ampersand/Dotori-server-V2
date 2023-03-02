package com.dotori.v2.domain.board.presentation.data.res

import com.dotori.v2.domain.member.enums.Role
import java.time.LocalDateTime

data class BoardResDto(
    val id: Long,
    val title: String,
    val roles: List<Role>,
    val createdDate: LocalDateTime
)