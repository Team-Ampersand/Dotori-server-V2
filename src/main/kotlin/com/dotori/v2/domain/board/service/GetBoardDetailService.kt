package com.dotori.v2.domain.board.service

import com.dotori.v2.domain.board.presentation.data.res.DetailBoardResDto

interface GetBoardDetailService {
    fun execute(boardId: Long): DetailBoardResDto
}