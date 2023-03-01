package com.dotori.v2.domain.board.service

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.presentation.data.req.ModifyBoardReqDto

interface ModifyBoardService {
    fun execute(modifyBoardReqDto: ModifyBoardReqDto, boardId: Long): Board
}