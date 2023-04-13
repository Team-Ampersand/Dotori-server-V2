package com.dotori.v2.domain.member.controller

import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.presentation.AuthController
import com.dotori.v2.domain.member.presentation.data.req.SignupReqDto
import com.dotori.v2.domain.member.presentation.data.res.RefreshResDto
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
import java.time.ZonedDateTime

class SignupControllerTest : BehaviorSpec({
    val targetService = mockk<SignupService>()
    val signInService = mockk<SignInService>()
    val refreshService = mockk<RefreshService>()
    val changePasswordService = mockk<ChangePasswordService>()
    val authController = AuthController(targetService, signInService, refreshService, changePasswordService)
    given("요청이 들어오면") {
        val req = SignupReqDto(
            memberName = "test",
            email = "test@gsm.hs.kr",
            gender = Gender.PENDING,
            password = "test",
            stuNum = "0000"
        )
        `when`("is received") {
            every { targetService.execute(req) } returns 0
            val response = authController.signup(req)
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { targetService.execute(req) }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})