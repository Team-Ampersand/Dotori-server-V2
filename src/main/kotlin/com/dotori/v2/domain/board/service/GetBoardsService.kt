package com.dotori.v2.domain.board.service

import com.dotori.v2.domain.board.presentation.data.res.ListBoardResDto

interface GetBoardsService {
    fun execute(): ListBoardResDto
}