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

class ApplySelfStudyControllerTest : BehaviorSpec({
    val service = mockk<ApplySelfStudyService>()
    val getSelfStudyInfoService = mockk<GetSelfStudyInfoService>()
    val cancelSelfStudyService = mockk<CancelSelfStudyService>()
    val getSelfStudyRankService = mockk<GetSelfStudyRankService>()
    val getSelfStudyByMemberNameService = mockk<GetSelfStudyByMemberNameService>()
    val getSelfStudyByStuNumService = mockk<GetSelfStudyByStuNumService>()
    val banSelfStudyService = mockk<BanSelfStudyService>()
    val cancelBanSelfStudyService = mockk<CancelBanSelfStudyService>()
    val councillorSelfStudyController = CouncillorSelfStudyController(service, getSelfStudyInfoService, getSelfStudyRankService, cancelSelfStudyService, getSelfStudyByMemberNameService, getSelfStudyByStuNumService, banSelfStudyService, cancelBanSelfStudyService)
    val developerSelfStudyController = DeveloperSelfStudyController(service, getSelfStudyInfoService, getSelfStudyRankService, cancelSelfStudyService, getSelfStudyByMemberNameService, getSelfStudyByStuNumService, cancelBanSelfStudyService, banSelfStudyService)
    val memberSelfStudyController = MemberSelfStudyController(service, getSelfStudyInfoService, getSelfStudyRankService, cancelSelfStudyService, getSelfStudyByMemberNameService, getSelfStudyByStuNumService)

    every { service.execute() } returns Unit
    given("요청이 들어오면") {
        `when`("councillorController is received") {
            val response = councillorSelfStudyController.applySelfStudy()
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("developerController is received") {
            val response = developerSelfStudyController.applySelfStudy()
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("memberController is received") {
            val response = memberSelfStudyController.applySelfStudy()
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})