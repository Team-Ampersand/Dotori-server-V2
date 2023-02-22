package com.dotori.v2.domain.main_page.service.impl

import com.dotori.v2.domain.board.domain.repository.BoardRepository
import com.dotori.v2.domain.board.exception.BoardNotExistsException
import com.dotori.v2.domain.main_page.presentation.dto.res.BoardAlarmResDto
import com.dotori.v2.domain.main_page.service.BoardAlarmService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class], readOnly = true)
class BoardAlarmServiceImpl(
    private val boardRepository: BoardRepository
) : BoardAlarmService {
    override fun execute(): BoardAlarmResDto {
        val board = boardRepository.findLastBoard()
            ?: throw BoardNotExistsException()
        return BoardAlarmResDto(board)
    }
}