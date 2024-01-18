package com.dotori.v2.domain.mainpage.presentation.dto.res

import com.dotori.v2.domain.board.presentation.data.dto.BoardDto

data class BoardAlarmResDto(
    val content: List<BoardDto>
)