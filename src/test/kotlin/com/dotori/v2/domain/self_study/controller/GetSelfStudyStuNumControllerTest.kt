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

class GetSelfStudyStuNumControllerTest : BehaviorSpec({
    val applySelfStudyService = mockk<ApplySelfStudyService>()
    val getSelfStudyInfoService = mockk<GetSelfStudyInfoService>()
    val getSelfStudyRankService = mockk<GetSelfStudyRankService>()
    val cancelSelfStudyService = mockk<CancelSelfStudyService>()
    val getSelfStudyByMemberNameService = mockk<GetSelfStudyByMemberNameService>()
    val getSelfStudyByMemberNumService = mockk<GetSelfStudyByStuNumService>()
    val banSelfStudyService = mockk<BanSelfStudyService>()
    val cancelBanSelfStudyService = mockk<CancelBanSelfStudyService>()
    val councillorSelfStudyController = CouncillorSelfStudyController(applySelfStudyService, getSelfStudyInfoService, getSelfStudyRankService, cancelSelfStudyService, getSelfStudyByMemberNameService, getSelfStudyByMemberNumService, banSelfStudyService, cancelBanSelfStudyService)
    val developerSelfStudyController = DeveloperSelfStudyController(applySelfStudyService, getSelfStudyInfoService, getSelfStudyRankService, cancelSelfStudyService, getSelfStudyByMemberNameService, getSelfStudyByMemberNumService, cancelBanSelfStudyService, banSelfStudyService)
    val memberSelfStudyController = MemberSelfStudyController(applySelfStudyService, getSelfStudyInfoService, getSelfStudyRankService, cancelSelfStudyService, getSelfStudyByMemberNameService, getSelfStudyByMemberNumService)
    val adminSelfStudyController = AdminSelfStudyController(getSelfStudyInfoService, getSelfStudyRankService, getSelfStudyByMemberNameService, getSelfStudyByMemberNumService, banSelfStudyService, cancelBanSelfStudyService)
    every { getSelfStudyByMemberNumService.execute("") } returns SelfStudyMemberListResDto(mutableListOf())
    given("요청이 들어오면") {
        `when`("councillorController is received") {
            val response = councillorSelfStudyController.getSelfStudyByStuNum("")
            then("서비스가 한번은 실행되어야 함") {
                verify { getSelfStudyByMemberNumService.execute("") }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("developerController is received") {
            val response = developerSelfStudyController.getSelfStudyByStuNum("")
            then("서비스가 한번은 실행되어야 함") {
                verify { getSelfStudyByMemberNumService.execute("") }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("memberController is received") {
            val response = memberSelfStudyController.getSelfStudyByStuNum("")
            then("서비스가 한번은 실행되어야 함") {
                verify { getSelfStudyByMemberNumService.execute("") }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("adminController is received") {
            val response = adminSelfStudyController.getSelfStudyByStuNum("")
            then("서비스가 한번은 실행되어야 함") {
                verify { getSelfStudyByMemberNumService.execute("") }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})