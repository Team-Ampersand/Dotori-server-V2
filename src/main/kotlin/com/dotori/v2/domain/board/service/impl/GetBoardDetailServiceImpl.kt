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
        val boardInfo: Board = boardRepository.findByIdOrNull(boardId) ?: throw BoardNotExistsException()
        val boardImages: List<BoardImage> = boardImageRepository.findAllByBoard_Id(boardInfo.id)
        if (boardImages.count() { true } == 0) {
            return toResponse(boardInfo)
        }
        return toResponse(boardInfo, boardImages)
    }

    private fun toResponse(board: Board): DetailBoardResDto =
        DetailBoardResDto(
            id = board.id,
            title = board.title,
            content = board.content,
            roles = board.member.roles,
            boardImage = listOf(),
            createdDate = board.createdDate,
            modifiedDate = board.modifiedDate
        )

    private fun toResponse(board: Board, boardImages: List<BoardImage>): DetailBoardResDto =
        DetailBoardResDto(
            id = board.id,
            title = board.title,
            content = board.content,
            roles = board.member.roles,
            boardImage = boardImages,
            createdDate = board.createdDate,
            modifiedDate = board.modifiedDate
        )

}