package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.auth.domain.entity.RefreshToken
import com.dotori.v2.domain.auth.domain.repository.RefreshTokenRepository
import com.dotori.v2.domain.auth.service.impl.RefreshTokenServiceImpl
import com.dotori.v2.domain.auth.util.AuthConverter
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.util.MemberUtil
import com.dotori.v2.global.security.jwt.TokenProvider
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.ZonedDateTime

class RefreshTokenServiceTest : BehaviorSpec({

    val tokenProvider = mockk<TokenProvider>()
    val refreshTokenRepository = mockk<RefreshTokenRepository>()
    val memberRepository = mockk<MemberRepository>()
    val authConverter = mockk<AuthConverter>()

    val refreshTokenService = RefreshTokenServiceImpl(
        tokenProvider = tokenProvider,
        refreshTokenRepository = refreshTokenRepository,
        authConverter = authConverter,
        memberRepository = memberRepository
    )

    given("refresh 토큰이 주어졌을때") {

        val refreshToken = "thisIsAfterRefreshToken"
        val accessToken = "thisIsAccessToken"
        val newRefreshToken = "thisIsBeforeRefreshToken"
        val role = Role.ROLE_MEMBER
        val accessExp = ZonedDateTime.now()
        val refreshExp = ZonedDateTime.now()
        val expiresAt = accessExp
        val email = "s22034@gsm.hs.kr"
        val member = MemberUtil.createMember(email = email)

        every { tokenProvider.parseToken(refreshToken) } returns refreshToken
        every { tokenProvider.exactEmailFromRefreshToken(refreshToken) } returns email
        every { tokenProvider.exactRoleFromRefreshToken(refreshToken) } returns role

        val refreshTokenEntity = RefreshToken(
            memberId = 1,
            token = refreshToken
        )


        every { refreshTokenRepository.findByToken(refreshToken) } returns refreshTokenEntity

        every { memberRepository.findByEmail(email) } returns member
        every { tokenProvider.generateAccessToken(email, role) } returns accessToken
        every { tokenProvider.generateRefreshToken(email, role) } returns newRefreshToken
        every { tokenProvider.accessExpiredTime } returns accessExp
        every { tokenProvider.refreshExpiredTime } returns refreshExp

        val newRefreshTokenEntity = RefreshToken(
            memberId = refreshTokenEntity.memberId,
            token = newRefreshToken
        )

        every { authConverter.toEntity(memberId = refreshTokenEntity.memberId, refreshToken = newRefreshToken) } returns newRefreshTokenEntity

        `when`("토큰들을 재발급 하였을때") {
            every { refreshTokenRepository.save(newRefreshTokenEntity) } returns newRefreshTokenEntity

            then("토큰 값 비교") {
                val value = refreshTokenService.execute(refreshToken)
                value.accessToken shouldBe accessToken
                value.refreshToken shouldBe newRefreshToken
                value.accessExp shouldBe accessExp
                value.refreshExp shouldBe refreshExp
                value.roles shouldBe member.roles
                value.expiresAt shouldBe expiresAt
            }
        }
    }
})