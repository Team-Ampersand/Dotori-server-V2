package com.dotori.v2.domain.auth.service.impl

import com.dotori.v2.domain.auth.domain.repository.RefreshTokenRepository
import com.dotori.v2.domain.auth.exception.ExpiredTokenException
import com.dotori.v2.domain.auth.exception.RoleNotExistException
import com.dotori.v2.domain.auth.exception.SecretMismatchException
import com.dotori.v2.domain.auth.exception.ServiceNotFoundException
import com.dotori.v2.domain.auth.presentation.data.dto.SignInGAuthDto
import com.dotori.v2.domain.auth.presentation.data.res.SignInResDto
import com.dotori.v2.domain.auth.service.SignInGAuthService
import com.dotori.v2.domain.auth.util.AuthConverter
import com.dotori.v2.domain.auth.util.AuthUtil
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import com.dotori.v2.global.config.gauth.properties.GAuthProperties
import com.dotori.v2.global.security.jwt.TokenProvider
import gauth.GAuth
import gauth.GAuthToken
import gauth.GAuthUserInfo
import gauth.exception.GAuthException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Service
@Transactional(rollbackFor = [Exception::class])
class SignInGAuthServiceImpl(
    private val gAuthProperties: GAuthProperties,
    private val memberRepository: MemberRepository,
    private val tokenProvider: TokenProvider,
    private val gAuth: GAuth,
    private val authUtil: AuthUtil,
    private val authConverter: AuthConverter,
) : SignInGAuthService {

    @Value("\${secret-email}")
    val secretEmail: String = ""
    override fun execute(signInDto: SignInGAuthDto): SignInResDto =
        runCatching {
            val gAuthToken: GAuthToken = gAuth.generateToken(
                signInDto.code,
                gAuthProperties.clientId,
                gAuthProperties.clientSecret,
                gAuthProperties.redirectUri
            )
            val gAuthUserInfo: GAuthUserInfo = gAuth.getUserInfo(gAuthToken.accessToken)
            val memberExist = memberRepository.existsByEmail(gAuthUserInfo.email)

            val role = getRoleByGAuthInfo(gAuthUserInfo.role, gAuthUserInfo.email)

            val member = createMemberWhenNotExistMember(
                isExist = memberExist,
                member = authConverter.toEntity(gAuthUserInfo, role)
            )

            val accessToken: String = tokenProvider.generateAccessToken(gAuthUserInfo.email, role)
            val refreshToken: String = tokenProvider.generateRefreshToken(gAuthUserInfo.email, role)
            val accessExp: ZonedDateTime = tokenProvider.accessExpiredTime
            val refreshExp: ZonedDateTime = tokenProvider.refreshExpiredTime

            authUtil.saveNewRefreshToken(member, refreshToken)

            SignInResDto(
                accessToken = accessToken,
                refreshToken = refreshToken,
                accessExp = accessExp,
                refreshExp = refreshExp
            )
        }.getOrElse { error ->
            when (error) {
                is GAuthException -> {
                    when (error.code) {
                        400 -> throw SecretMismatchException()
                        401 -> throw ExpiredTokenException()
                        404 -> throw ServiceNotFoundException()
                        else -> throw error
                    }
                }

                else -> throw error
            }
        }


    private fun getRoleByGAuthInfo(role: String, email: String): Role =
        when {
            email == secretEmail -> Role.ROLE_ADMIN
            role == "ROLE_STUDENT" -> Role.ROLE_MEMBER
            else -> throw RoleNotExistException()
        }

    private fun createMemberWhenNotExistMember(isExist: Boolean, member: Member): Member {
        return if (!isExist) {
            memberRepository.save(member)
        } else {
            memberRepository.findByEmail(member.email) ?: throw MemberNotFoundException()
        }
    }
}