package com.dotori.v2.domain.board.service.impl

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.domain.entity.BoardImage
import com.dotori.v2.domain.board.domain.repository.BoardImageRepository
import com.dotori.v2.domain.board.domain.repository.BoardRepository
import com.dotori.v2.domain.board.exception.BoardNotExistsException
import com.dotori.v2.domain.board.presentation.data.res.DetailBoardResDto
import com.dotori.v2.domain.board.service.GetBoardDetailService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class GetBoardDetailServiceImpl(
    private val boardRepository: BoardRepository,
    private val boardImageRepository: BoardImageRepository,
) : GetBoardDetailService {
    override fun execute(boardId: Long): DetailBoardResDto {
        val boardInfo: Board = boardRepository.findByIdOrNull(boardId)
            ?: throw BoardNotExistsException()

        return toResponse(boardInfo)
    }

    private fun toResponse(board: Board): DetailBoardResDto =
        DetailBoardResDto(
            id = board.id,
            title = board.title,
            content = board.content,
            role = board.member.roles[0],
            boardImage = board.boardImage,
            createdDate = board.createdDate,
            modifiedDate = board.modifiedDate
        )
}