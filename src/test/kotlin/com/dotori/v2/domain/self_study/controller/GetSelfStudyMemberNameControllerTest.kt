package com.dotori.v2.domain.self_study.controller

import com.dotori.v2.domain.self_study.presentation.admin.AdminSelfStudyController
import com.dotori.v2.domain.self_study.presentation.councillor.CouncillorSelfStudyController
import com.dotori.v2.domain.self_study.presentation.developer.DeveloperSelfStudyController
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.self_study.presentation.member.MemberSelfStudyController
import com.dotori.v2.domain.self_study.service.*
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class GetSelfStudyMemberNameControllerTest : BehaviorSpec({
    val applySelfStudyService = mockk<ApplySelfStudyService>()
    val getSelfStudyInfoService = mockk<GetSelfStudyInfoService>()
    val getSelfStudyRankService = mockk<GetSelfStudyRankService>()
    val cancelSelfStudyService = mockk<CancelSelfStudyService>()
    val service = mockk<GetSelfStudyByMemberNameService>()
    val councillorSelfStudyController = CouncillorSelfStudyController(applySelfStudyService, getSelfStudyInfoService, getSelfStudyRankService, cancelSelfStudyService, service)
    val developerSelfStudyController = DeveloperSelfStudyController(applySelfStudyService, getSelfStudyInfoService, getSelfStudyRankService, cancelSelfStudyService, service)
    val memberSelfStudyController = MemberSelfStudyController(applySelfStudyService, getSelfStudyInfoService, getSelfStudyRankService, cancelSelfStudyService, service)
    val adminSelfStudyController = AdminSelfStudyController(getSelfStudyInfoService, getSelfStudyRankService, service)
    every { service.execute("d") } returns SelfStudyMemberListResDto(mutableListOf())
    given("요청이 들어오면") {
        `when`("councillorController is received") {
            val response = councillorSelfStudyController.getSelfStudyByMemberName("d")
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute("d") }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("developerController is received") {
            val response = developerSelfStudyController.getSelfStudyByMemberName("d")
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute("d") }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("memberController is received") {
            val response = memberSelfStudyController.getSelfStudyByMemberName("d")
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute("d") }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("adminController is received") {
            val response = adminSelfStudyController.getSelfStudyByMemberName("d")
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute("d") }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})