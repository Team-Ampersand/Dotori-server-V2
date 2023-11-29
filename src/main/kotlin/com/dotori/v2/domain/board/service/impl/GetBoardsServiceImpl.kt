package com.dotori.v2.domain.board.service.impl

import com.dotori.v2.domain.board.domain.repository.BoardRepository
import com.dotori.v2.domain.board.presentation.data.res.BoardResDto
import com.dotori.v2.domain.board.presentation.data.res.ListBoardResDto
import com.dotori.v2.domain.board.service.GetBoardsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class GetBoardsServiceImpl(
    private val boardRepository: BoardRepository,
) : GetBoardsService {

    override fun execute(): ListBoardResDto = ListBoardResDto(
        boardList = boardRepository.findAllByOrderByCreatedDateDesc()
            .map { BoardResDto.of(it) }
    )

}