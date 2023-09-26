package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import com.dotori.v2.domain.member.exception.PasswordMismatchException
import com.dotori.v2.domain.member.presentation.data.req.SignInReqDto
import com.dotori.v2.domain.member.presentation.data.res.SignInResDto
import com.dotori.v2.domain.member.service.impl.SignInServiceImpl
import com.dotori.v2.global.security.jwt.TokenProvider
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.ZonedDateTime
import java.util.*

class SignInServiceTest : BehaviorSpec({
    val memberRepository = mockk<MemberRepository>()
    val passwordEncoder = mockk<PasswordEncoder>()
    val tokenProvider = mockk<TokenProvider>()
    val signInServiceImpl = SignInServiceImpl(memberRepository, passwordEncoder, tokenProvider)
    given("signInReqDto가 주어지고"){
        val reqDto = SignInReqDto(
            email = "test@gsm.hs.kr",
            password = "test"
        )
        val testMember = Member(
            memberName = "test",
            stuNum = "0000",
            email = "test@gsm.hs.kr",
            password = "test",
            gender = Gender.PENDING,
            roles = Collections.singletonList(Role.ROLE_MEMBER),
            ruleViolation = mutableListOf(),
            profileImage = null
        )

        init(memberRepository, reqDto, testMember, passwordEncoder, tokenProvider)
        `when`("signIn이 실행되면"){
            val target = SignInResDto(
                accessToken = "testAccessToken",
                refreshToken = "testRefreshToken",
                expiresAt = tokenProvider.accessExpiredTime,
                roles = testMember.roles
            )
            val result = signInServiceImpl.execute(reqDto)
            then("updateRefreshToken이 실행되어야함"){
                testMember.updateRefreshToken(tokenProvider.createRefreshToken(testMember.email))
            }
            then("결과값은 targetResult여야함"){
                result shouldBe target
            }
        }

        every { memberRepository.findByEmail(reqDto.email) } returns null
        `when`("해당 member가 존재하지 않을때"){
            then("MemberNotFoundException이 발생해야함"){
                val exception = shouldThrow<MemberNotFoundException> {
                    signInServiceImpl.execute(reqDto)
                }
                exception shouldBe MemberNotFoundException()
            }
        }
        init(memberRepository, reqDto, testMember, passwordEncoder, tokenProvider)

        every { passwordEncoder.matches(reqDto.password, testMember.password) } returns false
        `when`("패스워드가 일치하지 않을때"){
            then("PasswordNotMathException이 발생해야함"){
                    val exception = shouldThrow<PasswordMismatchException> {
                        signInServiceImpl.execute(reqDto)
                    }
                exception shouldBe PasswordMismatchException()
            }
        }
        init(memberRepository, reqDto, testMember, passwordEncoder, tokenProvider)
    }
})

private fun init(
    memberRepository: MemberRepository,
    reqDto: SignInReqDto,
    testMember: Member,
    passwordEncoder: PasswordEncoder,
    tokenProvider: TokenProvider
) {
    every { memberRepository.findByEmail(reqDto.email) } returns testMember
    every { passwordEncoder.matches(reqDto.password, testMember.password) } returns true
    every { tokenProvider.createAccessToken(testMember.email, testMember.roles) } returns "testAccessToken"
    every { tokenProvider.createRefreshToken(testMember.email) } returns "testRefreshToken"
    every { tokenProvider.accessExpiredTime } returns ZonedDateTime.now()
}