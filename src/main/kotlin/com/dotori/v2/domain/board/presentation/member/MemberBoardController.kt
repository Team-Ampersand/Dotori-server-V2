package com.dotori.v2.domain.board.presentation.member

import com.dotori.v2.domain.board.presentation.data.res.BoardResDto
import com.dotori.v2.domain.board.service.GetBoardsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2/member/board")
class MemberBoardController(
    private val getBoardsService: GetBoardsService
) {
    @GetMapping
    fun findBoards(): ResponseEntity<List<BoardResDto>> =
        ResponseEntity.status(HttpStatus.OK).body(getBoardsService.execute())
}