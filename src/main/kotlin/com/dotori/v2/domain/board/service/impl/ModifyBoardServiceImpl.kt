package com.dotori.v2.domain.board.service.impl

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.domain.repository.BoardRepository
import com.dotori.v2.domain.board.exception.BoardNotExistsException
import com.dotori.v2.domain.board.presentation.data.dto.ModifyBoardDto
import com.dotori.v2.domain.board.presentation.data.req.ModifyBoardReqDto
import com.dotori.v2.domain.board.service.ModifyBoardService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class ModifyBoardServiceImpl(
    private val boardRepository: BoardRepository
) : ModifyBoardService {
    override fun execute(modifyBoardReqDto: ModifyBoardReqDto, boardId: Long): Board {
        val boardInfo: Board = boardRepository.findBoardById(boardId) ?: throw BoardNotExistsException()
        toDto(modifyBoardReqDto)
            .let { boardInfo.updateBoard(title = it.title, content = it.content) }
        return boardInfo
    }

    private fun toDto(modifyBoardReqDto: ModifyBoardReqDto): ModifyBoardDto =
        ModifyBoardDto(
            title = modifyBoardReqDto.title,
            content = modifyBoardReqDto.content
        )
}