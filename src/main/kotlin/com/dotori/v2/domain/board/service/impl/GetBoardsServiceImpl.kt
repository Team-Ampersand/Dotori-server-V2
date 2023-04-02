package com.dotori.v2.domain.board.service.impl

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.domain.repository.BoardRepository
import com.dotori.v2.domain.board.presentation.data.res.BoardResDto
import com.dotori.v2.domain.board.presentation.data.res.ListBoardResDto
import com.dotori.v2.domain.board.service.GetBoardsService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class GetBoardsServiceImpl(
    private val boardRepository: BoardRepository,
) : GetBoardsService {
    override fun execute(): ListBoardResDto = ListBoardResDto(
        boardList = boardRepository.findAllByOrderByCreatedDateDesc()
            .map { it.toDto() }
    )

    private fun Board.toDto(): BoardResDto =
        BoardResDto(
            id = this.id,
            title = this.title,
            content = this.content,
            role = this.member.roles[0],
            createdDate = this.createdDate
        )

}