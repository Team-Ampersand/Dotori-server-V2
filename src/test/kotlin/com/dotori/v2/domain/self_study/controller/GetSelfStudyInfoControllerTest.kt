package com.dotori.v2.domain.self_study.controller

import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.self_study.presentation.councillor.CouncillorSelfStudyController
import com.dotori.v2.domain.self_study.presentation.developer.DeveloperSelfStudyController
import com.dotori.v2.domain.self_study.presentation.dto.SelfStudyInfoResDto
import com.dotori.v2.domain.self_study.presentation.member.MemberSelfStudyController
import com.dotori.v2.domain.self_study.service.ApplySelfStudyService
import com.dotori.v2.domain.self_study.service.GetSelfStudyInfoService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class GetSelfStudyInfoControllerTest : BehaviorSpec({
    val applySelfStudyService = mockk<ApplySelfStudyService>()
    val service = mockk<GetSelfStudyInfoService>()
    val councillorSelfStudyController = CouncillorSelfStudyController(applySelfStudyService, service)
    val developerSelfStudyController = DeveloperSelfStudyController(applySelfStudyService, service)
    val memberSelfStudyController = MemberSelfStudyController(applySelfStudyService, service)
    every { service.execute() } returns SelfStudyInfoResDto(1, SelfStudyStatus.CAN)
    given("요청이 들어오면") {
        `when`("councillorController is received") {
            val response = councillorSelfStudyController.getSelfStudyInfo()
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("developerController is received") {
            val response = developerSelfStudyController.getSelfStudyInfo()
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 2) { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("memberController is received") {
            val response = memberSelfStudyController.getSelfStudyInfo()
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 3) { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})