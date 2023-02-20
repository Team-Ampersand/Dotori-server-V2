package com.dotori.v2.domain.main_page.controller

import com.dotori.v2.domain.main_page.presentation.BoardAlarmController
import com.dotori.v2.domain.main_page.presentation.dto.res.BoardAlarmResDto
import com.dotori.v2.domain.main_page.service.BoardAlarmService
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
            val target = BoardAlarmResDto(1, "test", Collections.singletonList(Role.ROLE_DEVELOPER), LocalDate.now())
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