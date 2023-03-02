package com.dotori.v2.domain.board.service

import com.dotori.v2.domain.board.presentation.data.res.BoardResDto

interface GetBoardsService {
    fun execute(): List<BoardResDto>
}