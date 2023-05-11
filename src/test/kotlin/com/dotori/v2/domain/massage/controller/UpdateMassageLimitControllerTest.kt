package com.dotori.v2.domain.massage.controller

import com.dotori.v2.domain.massage.presentation.admin.AdminMassageController
import com.dotori.v2.domain.massage.presentation.councillor.CouncillorMassageController
import com.dotori.v2.domain.massage.presentation.developer.DeveloperMassageController
import com.dotori.v2.domain.massage.presentation.dto.req.MassageLimitReqDto
import com.dotori.v2.domain.massage.service.*
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class UpdateMassageLimitControllerTest : BehaviorSpec({
    val applyMassageService = mockk<ApplyMassageService>()
    val cancelMassageService = mockk<CancelMassageService>()
    val getMassageRankService = mockk<GetMassageRankService>()
    val getMassageInfoService = mockk<GetMassageInfoService>()
    val service = mockk<UpdateMassageLimitService>()

    val adminMassageController = AdminMassageController(getMassageRankService, getMassageInfoService, service)
    val councillorMassageController = CouncillorMassageController(applyMassageService, cancelMassageService, getMassageRankService, getMassageInfoService, service)
    val developerMassageController = DeveloperMassageController(applyMassageService, cancelMassageService, getMassageRankService, getMassageInfoService, service)

    val request = MassageLimitReqDto(limit = 6)
    every { service.execute(request) } returns Unit
    given("요청이 들어오면") {
        `when`("councillorController is received") {
            val response = councillorMassageController.updateMassageLimit(request)
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute(request) }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("developerController is received") {
            val response = developerMassageController.updateMassageLimit(request)
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute(request) }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("memberController is received") {
            val response = adminMassageController.updateMassageLimit(request)
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute(request) }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})