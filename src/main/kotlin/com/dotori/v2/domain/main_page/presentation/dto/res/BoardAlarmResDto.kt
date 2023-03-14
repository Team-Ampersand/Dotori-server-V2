package com.dotori.v2.domain.main_page.presentation.dto.res

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.member.enums.Role
import java.time.LocalDate

data class BoardAlarmResDto(
    val id: Long,
    val title: String,
    val writerRole: List<Role>,
    val lastBoardWriteDate: LocalDate
) {
    constructor(board: Board) : this(id = board.id, title = board.title, writerRole = board.member.roles, lastBoardWriteDate = board.createdDate!!.toLocalDate())
}