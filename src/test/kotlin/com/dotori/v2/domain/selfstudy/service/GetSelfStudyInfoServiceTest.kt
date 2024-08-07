package com.dotori.v2.domain.selfstudy.service

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.selfstudy.domain.entity.SelfStudyCount
import com.dotori.v2.domain.selfstudy.domain.repository.SelfStudyCountRepository
import com.dotori.v2.domain.selfstudy.presentation.dto.res.SelfStudyInfoResDto
import com.dotori.v2.domain.selfstudy.service.impl.GetSelfStudyInfoServiceImpl
import com.dotori.v2.domain.selfstudy.util.ValidDayOfWeekAndHourUtil
import com.dotori.v2.global.util.UserUtil
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.*

class GetSelfStudyInfoServiceTest : BehaviorSpec({
    val selfStudyCountRepository = mockk<SelfStudyCountRepository>()
    val validDayOfWeekAndHourUtil = mockk<ValidDayOfWeekAndHourUtil>()
    val userUtil = mockk<UserUtil>()

    val service = GetSelfStudyInfoServiceImpl(selfStudyCountRepository, validDayOfWeekAndHourUtil, userUtil)
    given("유저가 주어지고") {
        val testMember = Member(
            memberName = "test",
            stuNum = "2116",
            email = "test@gsm.hs.kr",
            password = "password1234",
            gender = Gender.MAN,
            roles = Collections.singletonList(Role.ROLE_MEMBER),
            ruleViolation = mutableListOf(),
            profileImage = null
        )
        every { userUtil.fetchCurrentUser() } returns testMember
        val selfStudyCount = SelfStudyCount(id = 1)
        every { selfStudyCountRepository.findSelfStudyCountById(1) } returns selfStudyCount
        every { validDayOfWeekAndHourUtil.isApplyValid() } returns true
        selfStudyCount.addCount()
        selfStudyCount.addCount()
        `when`("서비스를 실행하면") {
            val result = service.execute()
            then("결과값이 유저의 정보와 2가 반환되어야 함") {
                result shouldBe SelfStudyInfoResDto(selfStudyStatus = testMember.selfStudyStatus,limit = 50, count = selfStudyCount.count)
            }
        }
    }
})