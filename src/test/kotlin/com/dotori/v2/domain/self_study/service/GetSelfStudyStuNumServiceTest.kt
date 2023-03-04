package com.dotori.v2.domain.self_study.service

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.self_study.domain.entity.SelfStudy
import com.dotori.v2.domain.self_study.domain.repository.SelfStudyRepository
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberResDto
import com.dotori.v2.domain.self_study.service.impl.GetSelfStudyByStuNumServiceImpl
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.*

class GetSelfStudyStuNumServiceTest : BehaviorSpec({
    val selfStudyRepository = mockk<SelfStudyRepository>()
    val getSelfStudyByStuNumServiceImpl = GetSelfStudyByStuNumServiceImpl(selfStudyRepository)

    given("유저가 주어지고 자습이 주어지고"){
        val testMember = Member(
            memberName = "test",
            stuNum = "3217",
            email = "test@gsm.hs.kr",
            password = "test",
            gender = Gender.MAN,
            roles = Collections.singletonList(Role.ROLE_MEMBER)
        )
        val otherMember = Member(
            memberName = "other",
            stuNum = "3200",
            email = "other@gsm.hs.kr",
            password = "test",
            gender = Gender.MAN,
            roles = Collections.singletonList(Role.ROLE_MEMBER)
        )
        val selfStudy1 = SelfStudy(id = 1, testMember)
        val selfStudy2 = SelfStudy(id = 2, otherMember)
        val list = listOf(selfStudy1.member, selfStudy2.member)
        every { selfStudyRepository.findAllByStuNum("32") } returns list
        `when`("서비스를 실행하면"){
            val result = getSelfStudyByStuNumServiceImpl.execute("32")
            then("결과값은 otherMember가 리턴되어야함"){
                result shouldBe SelfStudyMemberListResDto(list.map { SelfStudyMemberResDto(it) })
            }
        }
    }
})