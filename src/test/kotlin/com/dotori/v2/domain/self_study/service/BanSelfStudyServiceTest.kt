package com.dotori.v2.domain.self_study.service

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import com.dotori.v2.domain.self_study.service.impl.BanSelfStudyServiceImpl
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate

class BanSelfStudyServiceTest : BehaviorSpec({
    val memberRepository = mockk<MemberRepository>()
    val banSelfStudyServiceImpl = BanSelfStudyServiceImpl(memberRepository)
    given("유저가 주어지고") {
        val testMember = Member(
            memberName = "test",
            stuNum = "2116",
            email = "test@gsm.hs.kr",
            gender = "MALE",
            roles = mutableListOf(Role.ROLE_MEMBER),
            ruleViolation = mutableListOf(),
            profileImage = null
        )

        every { memberRepository.findByIdOrNull(testMember.id) } returns testMember
        `when`("서비스를 실행할때") {
            banSelfStudyServiceImpl.execute(testMember.id)
            then("유저의 자습신청 상태는 불가능이여야함") {
                testMember.selfStudyStatus shouldBe SelfStudyStatus.IMPOSSIBLE
            }
            then("자습 금지 해제일은 일주일뒤여야함") {
                testMember.selfStudyExpiredDate!!.toLocalDate() shouldBe LocalDate.now().plusDays(7)
            }
        }

        every { memberRepository.findByIdOrNull(testMember.id) } returns null
        `when`("해당 유저가 없다면") {
            then("MemberNotFoundException이 발생해야함") {
                shouldThrow<MemberNotFoundException> {
                    banSelfStudyServiceImpl.execute(testMember.id)
                }
            }
        }
    }
})