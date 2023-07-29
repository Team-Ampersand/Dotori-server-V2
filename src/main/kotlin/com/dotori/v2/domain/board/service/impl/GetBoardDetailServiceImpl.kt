package com.dotori.v2.domain.board.service.impl

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.domain.entity.BoardImage
import com.dotori.v2.domain.board.domain.repository.BoardImageRepository
import com.dotori.v2.domain.board.domain.repository.BoardRepository
import com.dotori.v2.domain.board.exception.BoardNotExistsException
import com.dotori.v2.domain.board.presentation.data.dto.BoardImageDto
import com.dotori.v2.domain.board.presentation.data.res.DetailBoardResDto
import com.dotori.v2.domain.board.service.GetBoardDetailService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class GetBoardDetailServiceImpl(
    private val boardRepository: BoardRepository
) : GetBoardDetailService {
    override fun execute(boardId: Long): DetailBoardResDto {
        val board = boardRepository.findByIdOrNull(boardId)
            ?: throw BoardNotExistsException()

        return DetailBoardResDto.of(board)
    }


}