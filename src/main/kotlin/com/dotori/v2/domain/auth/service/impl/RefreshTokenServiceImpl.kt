package com.dotori.v2.domain.auth.service.impl

import com.dotori.v2.domain.auth.domain.repository.RefreshTokenRepository
import com.dotori.v2.domain.auth.util.AuthConverter
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.auth.presentation.data.res.RefreshResDto
import com.dotori.v2.domain.auth.service.RefreshTokenService
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import com.dotori.v2.global.security.exception.TokenExpiredException
import com.dotori.v2.global.security.exception.TokenInvalidException
import com.dotori.v2.global.security.jwt.TokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Service
@Transactional(rollbackFor = [Exception::class])
class RefreshTokenServiceImpl(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val memberRepository: MemberRepository,
    private val authConverter: AuthConverter,
    private val tokenProvider: TokenProvider,
) : RefreshTokenService {

    override fun execute(refreshToken: String): RefreshResDto {
        val refresh = tokenProvider.parseToken(refreshToken)
            ?: throw TokenInvalidException()

        val email: String = tokenProvider.exactEmailFromRefreshToken(refresh)

        val member = (memberRepository.findByEmail(email)
            ?: throw MemberNotFoundException())

        val role: Role = tokenProvider.exactRoleFromRefreshToken(refresh)

        val existingRefreshToken = refreshTokenRepository.findByToken(refresh)
            ?: throw TokenExpiredException()

        val newAccessToken = tokenProvider.generateAccessToken(email, role)
        val newRefreshToken = tokenProvider.generateRefreshToken(email, role)
        val accessExp: ZonedDateTime = tokenProvider.accessExpiredTime
        val refreshExp: ZonedDateTime = tokenProvider.refreshExpiredTime

        val newRefreshTokenEntity = authConverter.toEntity(
            memberId = existingRefreshToken.memberId,
            refreshToken = newRefreshToken
        )

        refreshTokenRepository.save(newRefreshTokenEntity)

        return RefreshResDto(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
            accessExp = accessExp,
            refreshExp = refreshExp,
            roles = member.roles
        )
    }
}