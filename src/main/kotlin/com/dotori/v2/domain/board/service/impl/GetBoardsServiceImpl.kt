package com.dotori.v2.domain.board.service.impl

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.domain.repository.BoardRepository
import com.dotori.v2.domain.board.presentation.data.res.BoardResDto
import com.dotori.v2.domain.board.presentation.data.res.ListBoardResDto
import com.dotori.v2.domain.board.service.GetBoardsService
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class GetBoardsServiceImpl(
    private val boardRepository: BoardRepository,
    private val userUtil: UserUtil
) : GetBoardsService {
    override fun execute(): ListBoardResDto {
        val memberInfo: Member = userUtil.fetchCurrentUser()
        return ListBoardResDto(
            content = boardRepository.findAll()
                .map { toDto(it, memberInfo) }
        )
    }

    private fun toDto(board: Board, member: Member): BoardResDto =
        BoardResDto(
            id = board.id,
            title = board.title,
            roles = member.roles,
            createdDate = board.createdDate
        )

}