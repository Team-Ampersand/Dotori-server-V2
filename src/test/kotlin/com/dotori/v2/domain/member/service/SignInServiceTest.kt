package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.auth.domain.entity.RefreshToken
import com.dotori.v2.domain.auth.domain.repository.RefreshTokenRepository
import com.dotori.v2.domain.auth.util.AuthConverter
import com.dotori.v2.domain.auth.util.impl.AuthUtilImpl
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.auth.presentation.data.dto.SignInGAuthDto
import com.dotori.v2.domain.auth.service.impl.SignInGAuthServiceImpl
import com.dotori.v2.global.config.gauth.properties.GAuthProperties
import com.dotori.v2.global.security.jwt.TokenProvider
import gauth.GAuth
import gauth.GAuthToken
import gauth.GAuthUserInfo
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class SignInServiceTest : BehaviorSpec({
    val clientId = "thisIsClientId"
    val clientSecret = "thisIsClientSecret"
    val redirectUri = "thisIsRedirectUri"

    val tokenProvider = mockk<TokenProvider>(relaxed = true)
    val gAuth = mockk<GAuth>()
    val memberRepository = mockk<MemberRepository>()
    val refreshTokenRepository = mockk<RefreshTokenRepository>()
    val authConverter = mockk<AuthConverter>()
    val gAuthProperties = GAuthProperties(clientId = clientId, clientSecret = clientSecret, redirectUri = redirectUri)
    val authUtil = AuthUtilImpl(
        refreshTokenRepository = refreshTokenRepository,
        authConverter = authConverter,
    )

    val signInService = SignInGAuthServiceImpl(
        gAuthProperties = gAuthProperties,
        memberRepository = memberRepository,
        tokenProvider = tokenProvider,
        gAuth = gAuth,
        authUtil = authUtil,
        authConverter = authConverter
    )

    given("gAuth로 로그인 요청이 갔을때") {
        val code = "thisIsCode"

        val map: Map<String, String> = mapOf(
            "accessToken" to "thisIsAccessToken",
            "refreshToken" to "thisIsRefreshToken"
        )


        val gAuthToken = GAuthToken(map)
        every {
            gAuth.generateToken(
                code,
                gAuthProperties.clientId,
                gAuthProperties.clientSecret,
                gAuthProperties.redirectUri
            )
        } returns gAuthToken

        val accessToken = "thisIsAccessToken"
        val refreshToken = "thisIsRefreshToken"

        val role = Role.ROLE_MEMBER
        val member = Member(1, "최민욱", "string1!","2216", "s22034@gsm.hs.kr", "MALE", mutableListOf(role), mutableListOf(), null)

        val userMap: Map<String, Any> = mapOf(
            "email" to member.email,
            "name" to "최민욱",
            "grade" to 2,
            "classNum" to 2,
            "num" to 16,
            "gender" to "MALE",
            "profileUrl" to "https://project-miso.s3.ap-northeast-2.amazonaws.com/file/Rectangle+2083.png",
            "role" to "ROLE_STUDENT"
        )

        val gAuthUserInfo = GAuthUserInfo(userMap)

        every {
            gAuth.getUserInfo(
                gAuthToken.accessToken
            )
        } returns gAuthUserInfo

        val refreshTokenEntity = RefreshToken(
            memberId = member.id,
            token = refreshToken
        )

        every {
            authUtil.saveNewRefreshToken(memberInfo = member, refreshToken = refreshToken)
        } returns refreshTokenEntity

        val signInDto = SignInGAuthDto(
            code = "thisIsCode"
        )

        every {
            tokenProvider.generateAccessToken(gAuthUserInfo.email, role)
        } returns accessToken

        every {
            tokenProvider.generateRefreshToken(gAuthUserInfo.email, role)
        } returns refreshToken

        val accessExp = tokenProvider.accessExpiredTime
        val refreshExp = tokenProvider.refreshExpiredTime

        every {
            tokenProvider.accessExpiredTime
        } returns accessExp

        every {
            tokenProvider.refreshExpiredTime
        } returns refreshExp

        every {
            authConverter.toEntity(gAuthUserInfo, role)
        } returns member

        every {
            authConverter.toEntity(member, refreshToken)
        } returns refreshTokenEntity

        every {
            memberRepository.save(member)
        } returns member

        memberRepository.save(member)

        every {
            refreshTokenRepository.save(refreshTokenEntity)
        } returns refreshTokenEntity

        refreshTokenRepository.save(refreshTokenEntity)

        `when`("signInDto가 주어지고 유저가 첫 로그인 시도 일때") {

            every {
                memberRepository.findByEmail(gAuthUserInfo.email)
            } returns null

            every {
                memberRepository.existsByEmail(gAuthUserInfo.email)
            } returns false

            then("user insert 쿼리가 실행되어야 함") {
                verify(exactly = 1) { memberRepository.save(member) }
            }

            then("refreshToken insert 쿼리가 실행되어야 함") {
                verify(exactly = 1) { refreshTokenRepository.save(refreshTokenEntity) }
            }

            val result = signInService.execute(signInDto)
            then("토큰 값 비교") {
                result.accessToken shouldBe accessToken
                result.refreshToken shouldBe refreshToken
                result.accessExp shouldBe accessExp
                result.refreshExp shouldBe refreshExp
            }

        }


        `when`("signInDto가 주어지고 유저가 로그인 했던 유저라면") {

            every {
                memberRepository.findByEmail(gAuthUserInfo.email)
            } returns member

            every {
                memberRepository.existsByEmail(gAuthUserInfo.email)
            } returns true

            then("user insert 쿼리가 실행되지 않는다") {
                verify { memberRepository.save(member) wasNot Called }
            }

            then("refreshToken update 쿼리가 실행되어야 함") {
                verify(exactly = 1) { refreshTokenRepository.save(refreshTokenEntity) }
            }

            val result = signInService.execute(signInDto)
            then("토큰 값 비교") {
                result.accessToken shouldBe accessToken
                result.refreshToken shouldBe refreshToken
                result.accessExp shouldBe accessExp
                result.refreshExp shouldBe refreshExp
            }
        }

    }

})