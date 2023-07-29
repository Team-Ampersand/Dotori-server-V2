package com.dotori.v2.domain.board.presentation.data.dto

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.member.enums.Role
import java.time.LocalDate

data class BoardDto(
    val id: Long,
    val title: String,
    val roles: List<Role>,
    val boardImages: List<BoardImageDto>,
    val createdDate: LocalDate
) {
    companion object {
        fun of(board: Board): BoardDto = BoardDto(
            id = board.id,
            title = board.title,
            roles = board.member.roles,
            boardImages = board.boardImage.map { BoardImageDto.of(it) },
            createdDate = board.createdDate.toLocalDate()
        )
    }
}