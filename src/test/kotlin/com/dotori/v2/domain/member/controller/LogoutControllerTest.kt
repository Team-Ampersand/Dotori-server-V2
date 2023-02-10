package com.dotori.v2.domain.member.controller

import com.dotori.v2.domain.member.presentation.AuthController
import com.dotori.v2.domain.member.presentation.dto.res.LogoutResDto
import com.dotori.v2.domain.member.service.LogoutService
import com.dotori.v2.domain.member.service.SignInService
import com.dotori.v2.domain.member.service.SignupService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class LogoutControllerTest : BehaviorSpec({
    val signInService = mockk<SignInService>()
    val signupService = mockk<SignupService>()
    val logoutService = mockk<LogoutService>()
    val authController = AuthController(signupService, signInService, logoutService)

    given("요청이 들어오면") {
        `when`("is received") {
            every { logoutService.execute() } returns LogoutResDto("")
            val response = authController.logout()
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { logoutService.execute() }
            }
            then("response status should be no content") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
}) {
}