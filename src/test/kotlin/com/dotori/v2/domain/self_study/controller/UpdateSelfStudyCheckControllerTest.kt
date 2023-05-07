package com.dotori.v2.domain.self_study.controller

import com.dotori.v2.domain.self_study.presentation.admin.AdminSelfStudyController
import com.dotori.v2.domain.self_study.presentation.councillor.CouncillorSelfStudyController
import com.dotori.v2.domain.self_study.presentation.developer.DeveloperSelfStudyController
import com.dotori.v2.domain.self_study.presentation.dto.req.SelfStudyCheckReqDto
import com.dotori.v2.domain.self_study.presentation.dto.req.SelfStudyLimitReqDto
import com.dotori.v2.domain.self_study.service.*
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class UpdateSelfStudyCheckControllerTest : BehaviorSpec({
    val applySelfStudyService = mockk<ApplySelfStudyService>()
    val getSelfStudyService = mockk<GetSelfStudyInfoService>()
    val getSelfStudyRankService = mockk<GetSelfStudyRankService>()
    val cancelSelfStudyService = mockk<CancelSelfStudyService>()
    val banSelfStudyService = mockk<BanSelfStudyService>()
    val cancelBanSelfStudyService = mockk<CancelBanSelfStudyService>()
    val changeSelfStudyLimitService = mockk<ChangeSelfStudyLimitService>()
    val selfStudyByStuNumAndNameService = mockk<GetSelfStudyByStuNumAndNameService>()
    val service = mockk<UpdateSelfStudyCheckService>()
    val councillorSelfStudyController = CouncillorSelfStudyController(applySelfStudyService, getSelfStudyService, getSelfStudyRankService, cancelSelfStudyService, banSelfStudyService, cancelBanSelfStudyService, changeSelfStudyLimitService, selfStudyByStuNumAndNameService, service)
    val developerSelfStudyController = DeveloperSelfStudyController(applySelfStudyService, getSelfStudyService, getSelfStudyRankService, cancelSelfStudyService, cancelBanSelfStudyService, banSelfStudyService, changeSelfStudyLimitService, selfStudyByStuNumAndNameService, service)
    val adminSelfStudyController = AdminSelfStudyController(getSelfStudyService, getSelfStudyRankService, banSelfStudyService, cancelBanSelfStudyService, changeSelfStudyLimitService, selfStudyByStuNumAndNameService, service)
    val request = SelfStudyCheckReqDto(false)
    every { service.execute(1, request) } returns Unit
    given("요청이 들어오면") {
        `when`("councillorController is received") {
            val response = councillorSelfStudyController.updateSelfStudyCheck(1, request)
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { service.execute(1, request) }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("developerController is received") {
            val response = developerSelfStudyController.updateSelfStudyCheck(1, request)
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 2) { service.execute(1, request) }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("adminController is received") {
            val response = adminSelfStudyController.updateSelfStudyCheck(1, request)
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 3) { service.execute(1, request) }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})