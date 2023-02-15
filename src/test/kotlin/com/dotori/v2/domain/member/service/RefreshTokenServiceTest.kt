package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.exception.TokenTypeNotValidException
import com.dotori.v2.domain.member.service.impl.RefreshTokenServiceImpl
import com.dotori.v2.global.config.security.jwt.TokenProvider
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.ZonedDateTime
import java.util.*

class RefreshTokenServiceTest : BehaviorSpec({
    val tokenProvider = mockk<TokenProvider>()
    val memberRepository = mockk<MemberRepository>()

    val refreshTokenServiceImpl = RefreshTokenServiceImpl(memberRepository, tokenProvider)
    given("리프레시 토큰이랑 유저가 주어지고"){
        val refreshToken = "testRefreshToken"
        val testMember = Member(
            memberName = "test",
            stuNum = "2116",
            email = "test@gsm.hs.kr",
            password = "test",
            gender = Gender.MAN,
            roles = Collections.singletonList(Role.ROLE_MEMBER)
        )
        init(tokenProvider, refreshToken, testMember, memberRepository)
        `when`("서비스를 실행하면"){
            val result = refreshTokenServiceImpl.execute(refreshToken)
            then("토큰이 새로 발급되어야함"){
                result.accessToken shouldBe "newAccess"
                result.refreshToken shouldBe "newRefresh"
            }
        }
        `when`("리프레시 토큰이 아닐때"){
            every { tokenProvider.isRefreshToken(refreshToken) } returns false
            then("TokenTypeNotValidException이 터져야함"){
                shouldThrow<TokenTypeNotValidException> {
                    refreshTokenServiceImpl.execute(refreshToken)
                }
            }
        }

    }

})

private fun init(
    tokenProvider: TokenProvider,
    refreshToken: String,
    testMember: Member,
    memberRepository: MemberRepository
) {
    every { tokenProvider.isRefreshToken(refreshToken) } returns true
    every { tokenProvider.getUserEmail(refreshToken) } returns testMember.email
    every { memberRepository.findByEmail(testMember.email) } returns testMember
    every { tokenProvider.createAccessToken(testMember.email, testMember.roles) } returns "newAccess"
    every { tokenProvider.createRefreshToken(testMember.email) } returns "newRefresh"
    every { tokenProvider.accessExpiredTime } returns ZonedDateTime.now()
}