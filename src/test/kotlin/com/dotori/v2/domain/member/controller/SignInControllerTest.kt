package com.dotori.v2.domain.member.controller

import com.dotori.v2.domain.auth.util.AuthConverter
import com.dotori.v2.domain.auth.util.impl.AuthConverterImpl
import com.dotori.v2.domain.member.presentation.AuthController
import com.dotori.v2.domain.member.presentation.data.dto.SignInDto
import com.dotori.v2.domain.member.presentation.data.req.SignInReqDto
import com.dotori.v2.domain.member.presentation.data.res.SignInResDto
import com.dotori.v2.domain.member.service.RefreshTokenService
import com.dotori.v2.domain.member.service.SignInService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import java.time.ZonedDateTime

class SignInControllerTest : BehaviorSpec({
    @Bean
    fun authConverter(): AuthConverter =
        AuthConverterImpl()

    val signInService = mockk<SignInService>()
    val refreshTokenService = mockk<RefreshTokenService>()
    val authController = AuthController(
        authConverter = authConverter(),
        signInService = signInService,
        refreshTokenService = refreshTokenService
    )

    given("요청이 들어오면") {
        val dto = SignInDto(
            code = "thisIsCode"
        )
        val request = SignInReqDto(
            code = "thisIsCode"
        )
        `when`("is received") {
            every { signInService.execute(dto) } returns SignInResDto(
                accessToken = "thisIsAccess",
                refreshToken = "thisIsRefresh",
                accessExp = ZonedDateTime.now(),
                refreshExp = ZonedDateTime.now()
            )
            val response = authController.signIn(request)

            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { signInService.execute(dto) }
            }
            then("response status should be success") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})