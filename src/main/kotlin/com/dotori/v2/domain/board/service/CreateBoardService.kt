package com.dotori.v2.domain.board.service

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.presentation.data.req.CreateBoardReqDto
import org.springframework.web.multipart.MultipartFile

interface CreateBoardService {
    fun execute(createBoardReqDto: CreateBoardReqDto, multipartFiles: List<MultipartFile>?): Board
}