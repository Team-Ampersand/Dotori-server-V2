package com.dotori.v2.domain.board.presentation.councillor

import com.dotori.v2.domain.board.presentation.data.req.CreateBoardReqDto
import com.dotori.v2.domain.board.service.CreateBoardService
import com.dotori.v2.domain.board.util.BoardConverter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RestController
@RequestMapping("/v2/councillor/board")
class CouncillorBoardController(
    private val createBoardService: CreateBoardService,
    private val boardConverter: BoardConverter
) {
    @PostMapping
    fun createBoard(
        @RequestPart(value = "files", required = false) multipartFiles: List<MultipartFile>?,
        @Valid @RequestPart(value = "boardDto") createBoardReqDto: CreateBoardReqDto
    ): ResponseEntity<Void> =
        boardConverter.toDto(createBoardReqDto)
            .let { createBoardService.execute(it, multipartFiles) }
            .run { ResponseEntity.status(HttpStatus.CREATED).build() }

}