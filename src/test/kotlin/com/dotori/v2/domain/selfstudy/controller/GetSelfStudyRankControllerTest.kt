package com.dotori.v2.domain.selfstudy.controller

import com.dotori.v2.domain.selfstudy.presentation.admin.AdminSelfStudyController
import com.dotori.v2.domain.selfstudy.presentation.councillor.CouncillorSelfStudyController
import com.dotori.v2.domain.selfstudy.presentation.developer.DeveloperSelfStudyController
import com.dotori.v2.domain.selfstudy.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.selfstudy.presentation.member.MemberSelfStudyController
import com.dotori.v2.domain.selfstudy.service.*
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class GetSelfStudyRankControllerTest : BehaviorSpec({
    val applySelfStudyService = mockk<ApplySelfStudyService>()
    val getSelfStudyInfoService = mockk<GetSelfStudyInfoService>()
    val service = mockk<GetSelfStudyRankService>()
    val cancelSelfStudyService = mockk<CancelSelfStudyService>()
    val banSelfStudyService = mockk<BanSelfStudyService>()
    val cancelBanSelfStudyService = mockk<CancelBanSelfStudyService>()
    val changeSelfStudyLimitService = mockk<ChangeSelfStudyLimitService>()
    val selfStudyByStuNumAndNameService = mockk<GetSelfStudyByStuNumAndNameService>()
    val updateSelfStudyCheckService = mockk<UpdateSelfStudyCheckService>()
    val adminSelfStudyController = AdminSelfStudyController(getSelfStudyInfoService, service, banSelfStudyService, cancelBanSelfStudyService, changeSelfStudyLimitService, selfStudyByStuNumAndNameService, updateSelfStudyCheckService)
    val councillorSelfStudyController = CouncillorSelfStudyController(applySelfStudyService, getSelfStudyInfoService, service, cancelSelfStudyService, banSelfStudyService, cancelBanSelfStudyService, changeSelfStudyLimitService, selfStudyByStuNumAndNameService, updateSelfStudyCheckService)
    val developerSelfStudyController = DeveloperSelfStudyController(applySelfStudyService, getSelfStudyInfoService, service, cancelSelfStudyService, cancelBanSelfStudyService, banSelfStudyService, changeSelfStudyLimitService, selfStudyByStuNumAndNameService, updateSelfStudyCheckService)
    val memberSelfStudyController = MemberSelfStudyController(applySelfStudyService, getSelfStudyInfoService, service, cancelSelfStudyService, selfStudyByStuNumAndNameService)
    every { service.execute() } returns SelfStudyMemberListResDto(mutableListOf())
    given("요청이 들어오면") {
        `when`("councillorController is received") {
            val response = councillorSelfStudyController.getSelfStudyRank()
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("developerController is received") {
            val response = developerSelfStudyController.getSelfStudyRank()
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("memberController is received") {
            val response = memberSelfStudyController.getSelfStudyRank()
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        `when`("adminController is received") {
            val response = adminSelfStudyController.getSelfStudyRank()
            then("서비스가 한번은 실행되어야 함") {
                verify { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})