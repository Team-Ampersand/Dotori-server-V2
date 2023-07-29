package com.dotori.v2.domain.board.presentation.data.res

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.member.enums.Role
import java.time.LocalDateTime

data class BoardResDto(
    val id: Long,
    val title: String,
    val content: String,
    val role: Role,
    val createdDate: LocalDateTime
) {
    companion object {
        fun of(board: Board) = BoardResDto(
                id = board.id,
                title = board.title,
                content = board.content,
                role = board.member.roles[0],
                createdDate = board.createdDate
            )
    }
}
