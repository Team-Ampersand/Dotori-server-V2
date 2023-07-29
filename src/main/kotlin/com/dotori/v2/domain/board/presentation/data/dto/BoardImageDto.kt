package com.dotori.v2.domain.board.presentation.data.dto

import com.dotori.v2.domain.board.domain.entity.BoardImage

data class BoardImageDto(
    val id: Long,
    val url: String
) {
    companion object {
        fun of(boardImage: BoardImage): BoardImageDto =
            BoardImageDto(
                id = boardImage.id,
                url = boardImage.url
            )
    }
}
