package com.dotori.v2.domain.self_study.service

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.self_study.domain.entity.SelfStudyCount
import com.dotori.v2.domain.self_study.domain.repository.SelfStudyCountRepository
import com.dotori.v2.domain.self_study.presentation.dto.SelfStudyInfoResDto
import com.dotori.v2.domain.self_study.service.impl.GetSelfStudyInfoServiceImpl
import com.dotori.v2.global.util.UserUtil
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.*

class GetSelfStudyInfoServiceTest : BehaviorSpec({
    val selfStudyCountRepository = mockk<SelfStudyCountRepository>()
    val userUtil = mockk<UserUtil>()

    val service = GetSelfStudyInfoServiceImpl(selfStudyCountRepository, userUtil)
    given("유저가 주어지고"){
        val testMember = Member(
            memberName = "test",
            stuNum = "2116",
            email = "test@gsm.hs.kr",
            password = "test",
            gender = Gender.MAN,
            roles = Collections.singletonList(Role.ROLE_MEMBER)
        )
        every { userUtil.fetchCurrentUser() } returns testMember
        val selfStudyCount = SelfStudyCount(id = 1)
        every { selfStudyCountRepository.findSelfStudyCountById(1) } returns selfStudyCount
        selfStudyCount.addCount()
        selfStudyCount.addCount()
        `when`("서비스를 실행하면"){
            val result = service.execute()
            then("결괴값이 유저의 정보와 2가 반환되어야함"){
                result shouldBe SelfStudyInfoResDto(selfStudyStatus = testMember.selfStudyStatus, count = selfStudyCount.count)
            }
        }
    }
})