package com.dotori.v2.domain.massage.controller

import com.dotori.v2.domain.massage.presentation.councillor.CouncillorMassageController
import com.dotori.v2.domain.massage.presentation.developer.DeveloperMassageController
import com.dotori.v2.domain.massage.presentation.member.MemberMassageController
import com.dotori.v2.domain.massage.service.*
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class MassageApplyControllerTest : BehaviorSpec({
    val service = mockk<ApplyMassageService>()
    val cancelMassageService = mockk<CancelMassageService>()
    val getMassageRankService = mockk<GetMassageRankService>()
    val getMassageInfoService = mockk<GetMassageInfoService>()
    val updateMassageLimitService = mockk<UpdateMassageLimitService>()

    val councillorMassageController = CouncillorMassageController(service, cancelMassageService, getMassageRankService, getMassageInfoService, updateMassageLimitService)
    val developerMassageController = DeveloperMassageController(service, cancelMassageService, getMassageRankService, getMassageInfoService, updateMassageLimitService)
    val memberMassageController = MemberMassageController(service, cancelMassageService, getMassageRankService, getMassageInfoService)

    every { service.execute() } returns Unit
    given("요청이 들어오면") {
        `when`("councillorController is received") {
            val response = councillorMassageController.applyMassage()
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("developerController is received") {
            val response = developerMassageController.applyMassage()
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("memberController is received") {
            val response = memberMassageController.applyMassage()
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})