package com.dotori.v2.domain.board.presentation.data.res

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.domain.entity.BoardImage
import com.dotori.v2.domain.board.presentation.data.dto.BoardImageDto
import com.dotori.v2.domain.member.enums.Role
import java.time.LocalDateTime

data class DetailBoardResDto(
    val id: Long,
    val title: String,
    val content: String,
    val role: Role,
    val boardImage: List<BoardImageDto>,
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime?
) {
    companion object {
        fun of(board: Board): DetailBoardResDto = DetailBoardResDto(
                id = board.id,
                title = board.title,
                content = board.content,
                role = board.member.roles[0],
                boardImage = board.boardImage.map { BoardImageDto.of(it) },
                createdDate = board.createdDate,
                modifiedDate = board.modifiedDate
            )
    }
}
