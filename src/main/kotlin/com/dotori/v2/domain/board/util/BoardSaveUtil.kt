package com.dotori.v2.domain.board.util

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.domain.entity.BoardImage
import com.dotori.v2.domain.board.domain.repository.BoardImageRepository
import com.dotori.v2.domain.board.domain.repository.BoardRepository
import org.springframework.stereotype.Component

@Component
class BoardSaveUtil(
    private val boardRepository: BoardRepository,
    private val boardImageRepository: BoardImageRepository
) {
    fun saveBoard(board: Board) =
        boardRepository.save(board)

    fun saveBoardImage(boardImage: BoardImage) =
        boardImageRepository.save(boardImage)
}