package com.dotori.v2.domain.member.controller

import com.dotori.v2.domain.member.presentation.AuthController
import com.dotori.v2.domain.member.presentation.dto.res.RefreshResDto
import com.dotori.v2.domain.member.service.*
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus
import java.time.ZonedDateTime

class RefreshTokenControllerTest : BehaviorSpec({
    val signupService = mockk<SignupService>()
    val signInService = mockk<SignInService>()
    val refreshService = mockk<RefreshService>()
    val authController = AuthController(signupService, signInService, refreshService)

    given("요청이 들어오면") {
        `when`("is received") {
            every { refreshService.execute("refreshToken") } returns RefreshResDto("newAccess", "newRefresh", ZonedDateTime.now())
            val response = authController.refresh("refreshToken")
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { refreshService.execute("refreshToken") }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})