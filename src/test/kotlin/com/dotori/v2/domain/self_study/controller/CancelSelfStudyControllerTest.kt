package com.dotori.v2.domain.self_study.controller

import com.dotori.v2.domain.self_study.presentation.councillor.CouncillorSelfStudyController
import com.dotori.v2.domain.self_study.presentation.developer.DeveloperSelfStudyController
import com.dotori.v2.domain.self_study.presentation.member.MemberSelfStudyController
import com.dotori.v2.domain.self_study.service.*
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class CancelSelfStudyControllerTest : BehaviorSpec({
    val applySelfStudyService = mockk<ApplySelfStudyService>()
    val getSelfStudyService = mockk<GetSelfStudyInfoService>()
    val getSelfStudyRankService = mockk<GetSelfStudyRankService>()
    val service = mockk<CancelSelfStudyService>()
    val banSelfStudyService = mockk<BanSelfStudyService>()
    val cancelBanSelfStudyService = mockk<CancelBanSelfStudyService>()
    val changeSelfStudyLimitService = mockk<ChangeSelfStudyLimitService>()
    val selfStudyByStuNumAndNameService = mockk<GetSelfStudyByStuNumAndNameService>()
    val updateSelfStudyCheckService = mockk<UpdateSelfStudyCheckService>()
    val councillorSelfStudyController = CouncillorSelfStudyController(applySelfStudyService, getSelfStudyService, getSelfStudyRankService, service, banSelfStudyService, cancelBanSelfStudyService, changeSelfStudyLimitService, selfStudyByStuNumAndNameService, updateSelfStudyCheckService)
    val developerSelfStudyController = DeveloperSelfStudyController(applySelfStudyService, getSelfStudyService, getSelfStudyRankService, service, cancelBanSelfStudyService, banSelfStudyService, changeSelfStudyLimitService, selfStudyByStuNumAndNameService, updateSelfStudyCheckService)
    val memberSelfStudyController = MemberSelfStudyController(applySelfStudyService, getSelfStudyService, getSelfStudyRankService, service, selfStudyByStuNumAndNameService)
    every { service.execute() } returns Unit
    given("요청이 들어오면") {
        `when`("councillorController is received") {
            val response = councillorSelfStudyController.cancelSelfStudy()
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("developerController is received") {
            val response = developerSelfStudyController.cancelSelfStudy()
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 2) { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("memberController is received") {
            val response = memberSelfStudyController.cancelSelfStudy()
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 3) { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})