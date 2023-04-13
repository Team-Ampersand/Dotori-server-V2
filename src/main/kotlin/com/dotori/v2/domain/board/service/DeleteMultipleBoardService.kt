package com.dotori.v2.domain.board.service

import com.dotori.v2.domain.board.presentation.data.req.DeleteMultipleBoardReqDto

interface DeleteMultipleBoardService {
    fun execute(deleteMultipleBoardReqDto: DeleteMultipleBoardReqDto)
}