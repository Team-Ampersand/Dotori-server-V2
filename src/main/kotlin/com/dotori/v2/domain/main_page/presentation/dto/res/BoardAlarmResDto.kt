package com.dotori.v2.domain.main_page.presentation.dto.res

import com.dotori.v2.domain.board.presentation.data.dto.BoardDto

data class BoardAlarmResDto(
    val content: List<BoardDto>
)