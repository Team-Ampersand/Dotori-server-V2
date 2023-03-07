package com.dotori.v2.domain.member.controller

import com.dotori.v2.domain.member.presentation.AuthController
import com.dotori.v2.domain.member.presentation.MemberController
import com.dotori.v2.domain.member.presentation.data.req.WithdrawalReqDto
import com.dotori.v2.domain.member.presentation.data.res.LogoutResDto
import com.dotori.v2.domain.member.service.*
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class WithdrawalControllerTest : BehaviorSpec({
    val logoutService = mockk<LogoutService>()
    val withdrawalService = mockk<WithdrawalService>()
    val changePasswordService = mockk<ChangePasswordService>()
    val authController = MemberController(logoutService, withdrawalService, changePasswordService)

    given("요청이 들어오면") {
        `when`("is received") {
            val reqDto = WithdrawalReqDto("test@gsm.hs.kr", "1234")
            every { withdrawalService.execute(reqDto) } returns Unit
            val response = authController.withdrawal(reqDto)
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { withdrawalService.execute(reqDto) }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})