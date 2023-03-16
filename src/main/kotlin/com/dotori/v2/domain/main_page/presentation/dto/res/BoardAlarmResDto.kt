package com.dotori.v2.domain.main_page.presentation.dto.res

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.presentation.data.dto.BoardDto
import com.dotori.v2.domain.board.presentation.data.res.BoardResDto
import com.dotori.v2.domain.member.enums.Role
import java.time.LocalDate

data class BoardAlarmResDto(
    val content: List<BoardDto>
)