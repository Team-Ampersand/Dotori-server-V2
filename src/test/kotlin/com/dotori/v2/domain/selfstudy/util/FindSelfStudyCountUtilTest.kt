package com.dotori.v2.domain.selfstudy.util

import com.dotori.v2.domain.selfstudy.domain.repository.SelfStudyCountRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.mockk
import io.mockk.verify

class FindSelfStudyCountUtilTest : BehaviorSpec({
    val selfStudyCountRepository = mockk<SelfStudyCountRepository>(relaxed = true)
    val selfStudyCountUtil = FindSelfStudyCountUtil(selfStudyCountRepository)

    given("selfStudyCountUtil이 주어지고") {
        `when`("유틸을 실행할때") {
            selfStudyCountUtil.findSelfStudyCount()
            then("selfStudyCountRepository는 조회 메서드를 실행해야됨") {
                verify(exactly = 1) { selfStudyCountRepository.findSelfStudyCountByIdForUpdate(1L) }
            }
        }
    }
})