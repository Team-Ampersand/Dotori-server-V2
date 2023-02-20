package com.dotori.v2.domain.board.service

import com.dotori.v2.domain.board.domain.entity.Board
import com.dotori.v2.domain.board.presentation.data.dto.CreateBoardDto
import org.springframework.web.multipart.MultipartFile

interface CreateBoardService {
    fun execute(createBoardDto: CreateBoardDto, multipartFiles: List<MultipartFile>?): Board
}