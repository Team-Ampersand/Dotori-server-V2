package com.dotori.v2.domain.member.service.impl

import com.dotori.v2.domain.auth.exception.RoleNotExistException
import com.dotori.v2.domain.auth.util.AuthUtil
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.presentation.data.dto.SignInDto
import com.dotori.v2.domain.member.presentation.data.res.SignInResDto
import com.dotori.v2.domain.member.service.SignInService
import com.dotori.v2.global.config.gauth.properties.GAuthProperties
import com.dotori.v2.global.security.jwt.TokenProvider
import gauth.GAuth
import gauth.GAuthToken
import gauth.GAuthUserInfo
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Service
@Transactional(rollbackFor = [Exception::class])
class SignInServiceImpl(
    private val gAuthProperties: GAuthProperties,
    private val memberRepository: MemberRepository,
    private val tokenProvider: TokenProvider,
    private val gAuth: GAuth,
    private val authUtil: AuthUtil,
) : SignInService {

    @Value("\${secret-email}")
    val secretEmail: String = ""

    override fun execute(signInDto: SignInDto): SignInResDto {
        val gAuthToken: GAuthToken = gAuth.generateToken(
            signInDto.code,
            gAuthProperties.clientId,
            gAuthProperties.clientSecret,
            gAuthProperties.redirectUri
        )
        val gAuthUserInfo: GAuthUserInfo = gAuth.getUserInfo(gAuthToken.accessToken)
        val role = getRoleByGauthInfo(gAuthUserInfo.role, gAuthUserInfo.email)

        val accessToken: String = tokenProvider.generateAccessToken(gAuthUserInfo.email, role)
        val refreshToken: String = tokenProvider.generateRefreshToken(gAuthUserInfo.email, role)
        val accessExp: ZonedDateTime = tokenProvider.accessExpiredTime
        val refreshExp: ZonedDateTime = tokenProvider.refreshExpiredTime

        when (role) {
            Role.ROLE_ADMIN -> {
                createAdminOrRefreshToken(gAuthUserInfo, refreshToken)
            } Role.ROLE_DEVELOPER -> {
                createDeveloperOrRefreshToken(gAuthUserInfo, refreshToken)
            } Role.ROLE_COUNCILLOR -> {
                createCouncillorOrRefreshToken(gAuthUserInfo, refreshToken)
            } else -> createMemberOrRefreshToken(gAuthUserInfo, refreshToken)
        }

        return SignInResDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
            accessExp = accessExp,
            refreshExp = refreshExp
        )
    }

    private fun getRoleByGauthInfo(role: String, email: String): Role {

        if (email == secretEmail) {
            return Role.ROLE_ADMIN
        }

        val user = memberRepository.findByEmail(email) ?:
        return when (role) {
            "ROLE_STUDENT" -> Role.ROLE_MEMBER
            else -> throw RoleNotExistException()
        }
        return Role.ROLE_MEMBER
    }

    private fun createMemberOrRefreshToken(gAuthUserInfo: GAuthUserInfo, refreshToken: String) {
        val userInfo = memberRepository.findByEmail(gAuthUserInfo.email)
        if (userInfo == null) {
            authUtil.saveNewUser(gAuthUserInfo, refreshToken)
        } else {
            authUtil.saveNewRefreshToken(userInfo, refreshToken)
        }
    }

    private fun createAdminOrRefreshToken(gAuthUserInfo: GAuthUserInfo, refreshToken: String) {
        val adminInfo = memberRepository.findByEmail(gAuthUserInfo.email)
        if (adminInfo == null) {
            authUtil.saveNewAdmin(gAuthUserInfo, refreshToken)
        } else {
            authUtil.saveNewRefreshToken(adminInfo, refreshToken)
        }
    }

    private fun createCouncillorOrRefreshToken(gAuthUserInfo: GAuthUserInfo, refreshToken: String) {
        val teacherInfo = memberRepository.findByEmail(gAuthUserInfo.email)
        if (teacherInfo == null) {
            authUtil.saveNewCouncillor(gAuthUserInfo, refreshToken)
        } else {
            authUtil.saveNewRefreshToken(teacherInfo, refreshToken)
        }
    }

    private fun createDeveloperOrRefreshToken(gAuthUserInfo: GAuthUserInfo, refreshToken: String) {
        val teacherInfo = memberRepository.findByEmail(gAuthUserInfo.email)
        if (teacherInfo == null) {
            authUtil.saveNewDeveloper(gAuthUserInfo, refreshToken)
        } else {
            authUtil.saveNewRefreshToken(teacherInfo, refreshToken)
        }
    }

}