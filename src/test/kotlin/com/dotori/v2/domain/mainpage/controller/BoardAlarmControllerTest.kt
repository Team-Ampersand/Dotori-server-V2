package com.dotori.v2.domain.mainpage.controller

import com.dotori.v2.domain.board.presentation.data.dto.BoardDto
import com.dotori.v2.domain.mainpage.presentation.BoardAlarmController
import com.dotori.v2.domain.mainpage.presentation.dto.res.BoardAlarmResDto
import com.dotori.v2.domain.mainpage.service.BoardAlarmService
import com.dotori.v2.domain.member.enums.Role
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus
import java.time.LocalDate
import java.util.Collections

class BoardAlarmControllerTest : BehaviorSpec({
    val boardAlarmService = mockk<BoardAlarmService>()

    val controller = BoardAlarmController(boardAlarmService)
    given("요청이 들어오면") {
        `when`("is received") {
            val boardDto = BoardDto(id =1, title = "test", roles = Collections.singletonList(Role.ROLE_DEVELOPER), createdDate = LocalDate.now(), boardImages = listOf())
            val target = BoardAlarmResDto(listOf(boardDto))
            every { boardAlarmService.execute() } returns target
            val response = controller.getBoardAlarm()
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { boardAlarmService.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
            then("response랑 목표값은 같아야한다"){
                response.body shouldBe target
            }
        }
    }
})