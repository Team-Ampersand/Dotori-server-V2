package com.dotori.v2.domain.self_study.service

import com.dotori.v2.domain.self_study.domain.entity.SelfStudyCount
import com.dotori.v2.domain.self_study.presentation.dto.req.SelfStudyLimitReqDto
import com.dotori.v2.domain.self_study.service.impl.ChangeSelfStudyLimitServiceImpl
import com.dotori.v2.domain.self_study.util.FindSelfStudyCountUtil
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class ChangeSelfStudyLimitServiceTest : BehaviorSpec({
    val findSelfStudyCountUtil = mockk<FindSelfStudyCountUtil>()
    val serviceImpl = ChangeSelfStudyLimitServiceImpl(findSelfStudyCountUtil)
    val selfStudyCount = SelfStudyCount(id = 1)
    given("request가 주어지고") {
        every { findSelfStudyCountUtil.findSelfStudyCount() } returns selfStudyCount
        val request = SelfStudyLimitReqDto(40)
        `when`("서비스를 실행할때") {
            serviceImpl.execute(request)
            then("selfStudyCount가 변경되어야함") {
                selfStudyCount.limit shouldBe request.limit
            }
        }
    }
})