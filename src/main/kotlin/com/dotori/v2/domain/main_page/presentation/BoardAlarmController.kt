package com.dotori.v2.domain.main_page.presentation

import com.dotori.v2.domain.main_page.presentation.dto.res.BoardAlarmResDto
import com.dotori.v2.domain.main_page.service.BoardAlarmService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2/home")
class BoardAlarmController(
    private val boardAlarmService: BoardAlarmService
) {
    @GetMapping("/board")
    fun getBoardAlarm(): ResponseEntity<BoardAlarmResDto> =
        ResponseEntity.ok(boardAlarmService.execute())

}