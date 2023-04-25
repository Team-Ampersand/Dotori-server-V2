package com.dotori.v2.domain.self_study.service

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import com.dotori.v2.domain.self_study.domain.entity.SelfStudy
import com.dotori.v2.domain.self_study.domain.repository.SelfStudyRepository
import com.dotori.v2.domain.self_study.exception.NotSelfStudyAppliedException
import com.dotori.v2.domain.self_study.presentation.dto.req.SelfStudyCheckReqDto
import com.dotori.v2.domain.self_study.service.impl.UpdateSelfStudyCheckServiceImpl
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.repository.findByIdOrNull
import java.util.*

class UpdateSelfStudyCheckServiceTest : BehaviorSpec({
    val selfStudyRepository = mockk<SelfStudyRepository>()
    val memberRepository = mockk<MemberRepository>()
    val updateSelfStudyCheckServiceImpl = UpdateSelfStudyCheckServiceImpl(selfStudyRepository, memberRepository)
    given("유저와 자습신청 정보가 있고 요청이 주어지면") {
        val testMember = Member(
            memberName = "test",
            stuNum = "2116",
            email = "test@gsm.hs.kr",
            password = "test",
            gender = Gender.MAN,
            roles = Collections.singletonList(Role.ROLE_MEMBER),
            ruleViolation = mutableListOf()
        )
        val selfStudy = SelfStudy(
            id = testMember.id,
            member = testMember
        )
        var request = SelfStudyCheckReqDto(selfStudyCheck = true)

        init(memberRepository, testMember, selfStudyRepository)
        `when`("selfStudyCheck를 true로 변경할때") {
            updateSelfStudyCheckServiceImpl.execute(testMember.id, request)
            then("testMember의 check가 true로 변경되어야함"){
                testMember.selfStudyCheck shouldBe true
            }
        }

        request = SelfStudyCheckReqDto(selfStudyCheck = false)
        `when`("selfStudyCheck를 false로 변경할때") {
            updateSelfStudyCheckServiceImpl.execute(testMember.id, request)
            then("testMember의 check가 false로 변경되어야함"){
                testMember.selfStudyCheck shouldBe false
            }
        }

        every { memberRepository.findByIdOrNull(testMember.id) } returns null
        `when`("해당 아이디를 가진 유저가 존재하지 않을때") {
            then("MemberNotFoundException이 발생해야함") {
                shouldThrow<MemberNotFoundException> {
                    updateSelfStudyCheckServiceImpl.execute(testMember.id, request)
                }
            }
        }
        init(memberRepository, testMember, selfStudyRepository)

        every { selfStudyRepository.existsByMember(testMember) } returns false
        `when`("해당 유저가 자습신청한 유저가 아닐때"){
            then("NotSelfStudyAppliedException이 발생해야함") {
                shouldThrow<NotSelfStudyAppliedException> {
                    updateSelfStudyCheckServiceImpl.execute(testMember.id, request)
                }
            }
        }
    }
})

private fun init(
    memberRepository: MemberRepository,
    testMember: Member,
    selfStudyRepository: SelfStudyRepository
) {
    every { memberRepository.findByIdOrNull(testMember.id) } returns testMember
    every { selfStudyRepository.existsByMember(testMember) } returns true
}