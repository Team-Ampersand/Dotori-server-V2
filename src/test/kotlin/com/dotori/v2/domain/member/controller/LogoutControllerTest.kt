package com.dotori.v2.domain.member.controller

import com.dotori.v2.domain.member.presentation.MemberController
import com.dotori.v2.domain.member.service.DeleteProfileImageService
import com.dotori.v2.domain.member.service.LogoutService
import com.dotori.v2.domain.member.service.UpdateProfileImageService
import com.dotori.v2.domain.member.service.UploadProfileImageService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class LogoutControllerTest : BehaviorSpec({
    val logoutService = mockk<LogoutService>()
    val uploadProfileImageService = mockk<UploadProfileImageService>()
    val updateProfileImageService = mockk<UpdateProfileImageService>()
    val deleteProfileImageService = mockk<DeleteProfileImageService>()
    val authController = MemberController(logoutService, uploadProfileImageService, updateProfileImageService, deleteProfileImageService)

    given("요청이 들어오면") {
        `when`("is received") {
            every { logoutService.execute() } returns Unit
            val response = authController.logout()
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { logoutService.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})