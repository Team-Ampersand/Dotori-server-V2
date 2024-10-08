package com.dotori.v2.domain.auth.service.impl

import com.dotori.v2.domain.auth.domain.entity.RefreshToken
import com.dotori.v2.domain.auth.domain.repository.RefreshTokenRepository
import com.dotori.v2.domain.auth.presentation.data.dto.SignInEmailAndPasswordDto
import com.dotori.v2.domain.auth.presentation.data.res.SignInResDto
import com.dotori.v2.domain.auth.service.SignInEmailAndPasswordService
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import com.dotori.v2.domain.member.exception.PasswordMismatchException
import com.dotori.v2.global.security.jwt.TokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class SignInEmailAndPasswordServiceImpl(
    private val memberRepository: MemberRepository,
    private val tokenProvider: TokenProvider,
    private val passwordEncoder: PasswordEncoder,
    private val refreshTokenRepository: RefreshTokenRepository
) : SignInEmailAndPasswordService {
    override fun execute(signInEmailAndPasswordDto: SignInEmailAndPasswordDto): SignInResDto {

        val member = memberRepository.findByEmail(signInEmailAndPasswordDto.email)
            ?: throw MemberNotFoundException()

        if (!passwordEncoder.matches(signInEmailAndPasswordDto.password, member.password)) {
            throw PasswordMismatchException()
        }

        val accessToken =
            tokenProvider.generateAccessToken(signInEmailAndPasswordDto.email, role = member.roles.first())
        val accessExp = tokenProvider.accessExpiredTime
        val expiresAt = tokenProvider.accessExpiredTime
        val refreshToken =
            tokenProvider.generateRefreshToken(signInEmailAndPasswordDto.email, role = member.roles.first())
        val refreshExp = tokenProvider.refreshExpiredTime

        refreshTokenRepository.save(
            RefreshToken(
                memberId = member.id,
                token = refreshToken
            )
        )
        return toResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            accessExp = accessExp,
            refreshExp = refreshExp,
            roles = member.roles,
            expiresAt = expiresAt
        )
    }

    private fun toResponse(
        accessToken: String,
        refreshToken: String,
        accessExp: ZonedDateTime,
        refreshExp: ZonedDateTime,
        roles: MutableList<Role>,
        expiresAt: ZonedDateTime
    ): SignInResDto =
        SignInResDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
            accessExp = accessExp,
            refreshExp = refreshExp,
            roles = roles,
            expiresAt = expiresAt
        )
}