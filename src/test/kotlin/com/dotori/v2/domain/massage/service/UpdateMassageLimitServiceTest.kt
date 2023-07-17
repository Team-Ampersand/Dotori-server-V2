package com.dotori.v2.domain.massage.service

import com.dotori.v2.domain.massage.presentation.dto.req.MassageLimitReqDto
import com.dotori.v2.domain.massage.service.impl.UpdateMassageLimitServiceImpl
import com.dotori.v2.domain.massage.util.FindMassageCountUtil
import com.dotori.v2.domain.massage.util.MassageUtil
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class UpdateMassageLimitServiceTest : BehaviorSpec({
    val findMassageCountUtil = mockk<FindMassageCountUtil>()
    val updateMassageLimitServiceImpl = UpdateMassageLimitServiceImpl(findMassageCountUtil)
    given("요청이 주어지고") {
        val massageCount = MassageUtil.createMassageCount()
        every { findMassageCountUtil.findMassageCount() } returns massageCount
        val request = MassageLimitReqDto(limit = 10)
        `when`("서비스를 실행하면") {
            updateMassageLimitServiceImpl.execute(request)
            then("massageCount의 limit는 request의 limit가 되야함"){
                massageCount.limit shouldBe request.limit
            }
        }
    }
})