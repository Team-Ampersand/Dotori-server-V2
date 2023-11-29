package com.dotori.v2.domain.member.controller

import com.dotori.v2.domain.auth.util.AuthConverter
import com.dotori.v2.domain.auth.util.impl.AuthConverterImpl
import com.dotori.v2.domain.member.presentation.AuthController
import com.dotori.v2.domain.member.presentation.data.res.RefreshResDto
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

class RefreshTokenControllerTest : BehaviorSpec({

    @Bean
    fun authConverter(): AuthConverter =
        AuthConverterImpl()

    val refreshTokenService = mockk<RefreshTokenService>()
    val signInService = mockk<SignInService>()
    val authController = AuthController(
        authConverter = authConverter(),
        signInService = signInService,
        refreshTokenService = refreshTokenService,
    )

    given("요청이 들어오면") {
        val refreshToken = "thisIsRefresh"
        `when`("is received") {
            every {
                refreshTokenService.execute(refreshToken)
            } returns RefreshResDto(
                accessToken = "thisIsAccess",
                refreshToken = "thisIsRefresh",
                accessExp = ZonedDateTime.now(),
                refreshExp = ZonedDateTime.now()
            )

            val response = authController.getNewRefreshToken(refreshToken)

            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { refreshTokenService.execute(refreshToken) }
            }
            then("response status should be success") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})