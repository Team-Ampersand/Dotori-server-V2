package com.dotori.v2.domain.massage.controller

import com.dotori.v2.domain.massage.presentation.admin.AdminMassageController
import com.dotori.v2.domain.massage.presentation.councillor.CouncillorMassageController
import com.dotori.v2.domain.massage.presentation.developer.DeveloperMassageController
import com.dotori.v2.domain.massage.presentation.dto.res.MassageInfoResDto
import com.dotori.v2.domain.massage.presentation.member.MemberMassageController
import com.dotori.v2.domain.massage.service.*
import com.dotori.v2.domain.member.enums.MassageStatus
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class GetMassageInfoControllerTest : BehaviorSpec({
    val applyMassageService = mockk<ApplyMassageService>()
    val cancelMassageService = mockk<CancelMassageService>()
    val getMassageRankService = mockk<GetMassageRankService>()
    val service = mockk<GetMassageInfoService>()
    val updateMassageLimitService = mockk<UpdateMassageLimitService>()

    val adminMassageController = AdminMassageController(getMassageRankService, service, updateMassageLimitService)
    val councillorMassageController = CouncillorMassageController(applyMassageService, cancelMassageService, getMassageRankService, service, updateMassageLimitService)
    val developerMassageController = DeveloperMassageController(applyMassageService, cancelMassageService, getMassageRankService, service, updateMassageLimitService)
    val memberMassageController = MemberMassageController(applyMassageService, cancelMassageService, getMassageRankService, service)

    every { service.execute() } returns MassageInfoResDto(1, MassageStatus.CAN, 1)
    given("요청이 들어오면") {
        `when`("councillorController is received") {
            val response = councillorMassageController.getMassageInfo()
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("developerController is received") {
            val response = developerMassageController.getMassageInfo()
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("memberController is received") {
            val response = memberMassageController.getMassageInfo()
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("adminController is received") {
            val response = adminMassageController.getMassageInfo()
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})