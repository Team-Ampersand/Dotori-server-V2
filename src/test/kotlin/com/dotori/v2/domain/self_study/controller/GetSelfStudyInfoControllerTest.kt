package com.dotori.v2.domain.self_study.controller

import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.self_study.presentation.councillor.CouncillorSelfStudyController
import com.dotori.v2.domain.self_study.presentation.developer.DeveloperSelfStudyController
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyInfoResDto
import com.dotori.v2.domain.self_study.presentation.member.MemberSelfStudyController
import com.dotori.v2.domain.self_study.service.ApplySelfStudyService
import com.dotori.v2.domain.self_study.service.CancelSelfStudyService
import com.dotori.v2.domain.self_study.service.GetSelfStudyInfoService
import com.dotori.v2.domain.self_study.service.GetSelfStudyRankService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class GetSelfStudyInfoControllerTest : BehaviorSpec({
    val applySelfStudyService = mockk<ApplySelfStudyService>()
    val service = mockk<GetSelfStudyInfoService>()
    val cancelSelfStudyService = mockk<CancelSelfStudyService>()
    val getSelfStudyRankService = mockk<GetSelfStudyRankService>()
    val councillorSelfStudyController = CouncillorSelfStudyController(applySelfStudyService, service, getSelfStudyRankService, cancelSelfStudyService)
    val developerSelfStudyController = DeveloperSelfStudyController(applySelfStudyService, service, getSelfStudyRankService, cancelSelfStudyService)
    val memberSelfStudyController = MemberSelfStudyController(applySelfStudyService, service, getSelfStudyRankService, cancelSelfStudyService)
    every { service.execute() } returns SelfStudyInfoResDto(1, SelfStudyStatus.CAN)
    given("요청이 들어오면") {
        `when`("councillorController is received") {
            val response = councillorSelfStudyController.getSelfStudyInfo()
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("developerController is received") {
            val response = developerSelfStudyController.getSelfStudyInfo()
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("memberController is received") {
            val response = memberSelfStudyController.getSelfStudyInfo()
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})