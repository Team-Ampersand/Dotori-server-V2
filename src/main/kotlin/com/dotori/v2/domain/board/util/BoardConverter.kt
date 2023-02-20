package com.dotori.v2.domain.board.util

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.domain.entity.BoardImage
import com.dotori.v2.domain.board.presentation.data.dto.CreateBoardDto
import com.dotori.v2.domain.board.presentation.data.req.CreateBoardReqDto
import com.dotori.v2.domain.member.domain.entity.Member

interface BoardConverter {
    fun toDto(createBoardReqDto: CreateBoardReqDto): CreateBoardDto
    fun toEntity(createBoardDto: CreateBoardDto, member: Member): Board
    fun toEntity(board: Board, uploadFileUrl: String): BoardImage
}