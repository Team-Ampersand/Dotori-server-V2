package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import com.dotori.v2.domain.member.exception.MemberNotSameException
import com.dotori.v2.domain.member.exception.PasswordMismatchException
import com.dotori.v2.domain.member.presentation.data.req.WithdrawalReqDto
import com.dotori.v2.domain.member.service.impl.WithdrawalServiceImpl
import com.dotori.v2.global.util.UserUtil
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

class WithdrawalServiceTest : BehaviorSpec({
    val memberRepository = mockk<MemberRepository>()
    val userUtil = mockk<UserUtil>()
    val passwordEncoder = mockk<PasswordEncoder>()
    val withdrawalServiceImpl = WithdrawalServiceImpl(memberRepository, passwordEncoder, userUtil)
    given("유저와 dto가 주어지고") {
        val testMember = Member(
            memberName = "test",
            stuNum = "2116",
            email = "test@gsm.hs.kr",
            password = "test",
            gender = Gender.MAN,
            roles = Collections.singletonList(Role.ROLE_MEMBER),
            ruleViolation = mutableListOf()
        )
        val request = WithdrawalReqDto("test@gsm.hs.kr", "1234")
        init(userUtil, testMember, memberRepository, request, passwordEncoder)
        `when`("서비스를 실행하면") {
            withdrawalServiceImpl.execute(request)
            then("delete가 실행되어야함") {
                verify(exactly = 1) { memberRepository.delete(testMember) }
            }
        }
        `when`("유저를 찾을 수 없을때") {
            every { memberRepository.findByEmail(request.email) } returns null
            then("MemberNotFoundException이 터져야함") {
                shouldThrow<MemberNotFoundException> {
                    withdrawalServiceImpl.execute(request)
                }
            }
        }
        init(userUtil, testMember, memberRepository, request, passwordEncoder)
        `when`("패스워드가 일치하지 않을때") {
            every { passwordEncoder.matches(request.password, testMember.password) } returns false
            then("PasswordMismatchException이 터져야함") {
                shouldThrow<PasswordMismatchException> {
                    withdrawalServiceImpl.execute(request)
                }
            }
        }
        init(userUtil, testMember, memberRepository, request, passwordEncoder)
        `when`("현재 로그인된 유저와 요청에서 가져온 유저가 일치하지 않을때") {
            val otherMember = Member(
                0,
                "test1",
                "0000",
                "other@gsm.hs.kr",
                "test1",
                Gender.MAN,
                Collections.singletonList(Role.ROLE_MEMBER),
                ruleViolation = mutableListOf()
            )
            every { memberRepository.findByEmail(request.email) } returns otherMember
            every { passwordEncoder.matches(request.password, otherMember.password) } returns true
            then("MemberNotSameException이 터져야함") {
                shouldThrow<MemberNotSameException> {
                    withdrawalServiceImpl.execute(request)
                }
            }
        }
    }
})

private fun init(
    userUtil: UserUtil,
    testMember: Member,
    memberRepository: MemberRepository,
    request: WithdrawalReqDto,
    passwordEncoder: PasswordEncoder
) {
    every { userUtil.fetchCurrentUser() } returns testMember
    every { memberRepository.findByEmail(request.email) } returns testMember
    every { passwordEncoder.matches(request.password, testMember.password) } returns true
    every { memberRepository.delete(testMember) } returns Unit
}