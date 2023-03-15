package com.dotori.v2.domain.self_study.service

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.self_study.domain.entity.SelfStudy
import com.dotori.v2.domain.self_study.domain.repository.SelfStudyRepository
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberResDto
import com.dotori.v2.domain.self_study.service.impl.GetSelfStudyRankServiceImpl
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class GetSelfStudyServiceTest : BehaviorSpec({
    val selfStudyRepository = mockk<SelfStudyRepository>()

    val getSelfStudyRankServiceImpl = GetSelfStudyRankServiceImpl(selfStudyRepository)
    given("윺저와 자습이 주어지고") {
        val testMember = Member(
            memberName = "test",
            stuNum = "2116",
            email = "test@gsm.hs.kr",
            password = "test",
            gender = Gender.MAN,
            roles = Collections.singletonList(Role.ROLE_MEMBER),
            ruleViolation = mutableListOf()
        )
        val selfStudy1 = SelfStudy(id = 1, testMember)
        val selfStudy2 = SelfStudy(id = 2, testMember)
        val list = listOf(selfStudy1, selfStudy2)
        every { selfStudyRepository.findAllOrderByCreatedDateAsc() } returns list
        `when`("서비스를 실행하면") {
            val result = getSelfStudyRankServiceImpl.execute()
            then("findAllOrderByCreatedDateAsc 메서드가 실행되어야함") {
                verify { selfStudyRepository.findAllOrderByCreatedDateAsc() }
            }
            then("result는 list의 값이랑 같아야함"){
                result shouldBe SelfStudyMemberListResDto(list.mapIndexed {index, it -> SelfStudyMemberResDto(index + 1L, it.member) })
            }
        }

    }

})