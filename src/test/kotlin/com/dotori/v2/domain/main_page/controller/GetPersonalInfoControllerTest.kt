package com.dotori.v2.domain.main_page.controller

import com.dotori.v2.domain.main_page.presentation.GetPersonalInfoController
import com.dotori.v2.domain.main_page.presentation.dto.res.PersonalInfoResDto
import com.dotori.v2.domain.main_page.service.GetPersonalInfoService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class GetPersonalInfoControllerTest : BehaviorSpec({
    val getPersonalInfoService = mockk<GetPersonalInfoService>()
    val controller = GetPersonalInfoController(getPersonalInfoService)

    given("요청이 들어오면") {
        `when`("is received") {
            val target = PersonalInfoResDto(1, "test", "test", "MALE", null)
            every { getPersonalInfoService.execute() } returns target
            val response = controller.getPersonalInfo()
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { getPersonalInfoService.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
            then("응답 바디에는 유저정보가 들어가있어야함") {
                response.body shouldBe target
            }
        }
    }
})