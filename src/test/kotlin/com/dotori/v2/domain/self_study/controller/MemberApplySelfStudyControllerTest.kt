package com.dotori.v2.domain.self_study.controller

import com.dotori.v2.domain.self_study.presentation.member.MemberSelfStudyController
import com.dotori.v2.domain.self_study.service.ApplySelfStudyService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class MemberApplySelfStudyControllerTest : BehaviorSpec({
    val service = mockk<ApplySelfStudyService>()

    val memberSelfStudyController = MemberSelfStudyController(service)
    given("요청이 들어오면") {
        `when`("memberController is received") {
            every { service.execute() } returns Unit
            val response = memberSelfStudyController.applySelfStudy()
            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { service.execute() }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
        }
    }
})