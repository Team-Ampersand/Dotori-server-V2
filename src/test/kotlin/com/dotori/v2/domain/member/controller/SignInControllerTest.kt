package com.dotori.v2.domain.member.controller

import com.dotori.v2.domain.member.presentation.AuthController
import com.dotori.v2.domain.member.presentation.data.req.SignInReqDto
import com.dotori.v2.domain.member.presentation.data.res.RefreshResDto
import com.dotori.v2.domain.member.presentation.data.res.SignInResDto
import com.dotori.v2.domain.member.service.ChangePasswordService
import com.dotori.v2.domain.member.service.RefreshService
import com.dotori.v2.domain.member.service.SignInService
import com.dotori.v2.domain.member.service.SignupService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus
import java.time.LocalDate
import java.time.ZonedDateTime

class SignInControllerTest : BehaviorSpec({
    val signupService = mockk<SignupService>()
    val signInService = mockk<SignInService>()
    val refreshService = mockk<RefreshService>()
    val changePasswordService = mockk<ChangePasswordService>()
    val authController = AuthController(signupService, signInService, refreshService, changePasswordService)

    given("요청이 들어오면") {
        val reqDto = SignInReqDto(email = "test@gsm.hs.kr", password = "test")
        `when`("is received") {
            val resDto = SignInResDto(
                accessToken = "testAccessToken",
                refreshToken = "testRefreshToken",
                expiresAt = ZonedDateTime.now()
            )
            every { signInService.execute(reqDto) } returns resDto
            val response = authController.signIn(reqDto)
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { signInService.execute(reqDto) }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})