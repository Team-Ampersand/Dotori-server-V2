package com.dotori.v2.domain.mainpage.service

import com.dotori.v2.domain.mainpage.presentation.dto.res.PersonalInfoResDto
import com.dotori.v2.domain.mainpage.service.impl.GetPersonalInfoServiceImpl
import com.dotori.v2.domain.member.util.MemberUtil
import com.dotori.v2.global.util.UserUtil
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class GetPersonalInfoServiceTest : BehaviorSpec({
    val userUtil = mockk<UserUtil>()
    val getPersonalInfoServiceImpl = GetPersonalInfoServiceImpl(userUtil)

    given("유저가 주어지고") {
        val testMember = MemberUtil.createMember()
        every { userUtil.fetchCurrentUser() } returns testMember
        `when`("서비스를 실행하면") {
            val result = getPersonalInfoServiceImpl.execute()
            then("유저의 정보가 dto로 반환되어야한다") {
                result shouldBe PersonalInfoResDto(testMember)
            }
        }
    }
})