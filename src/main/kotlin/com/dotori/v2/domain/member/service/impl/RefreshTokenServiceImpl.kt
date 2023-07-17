package com.dotori.v2.domain.member.service.impl

import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import com.dotori.v2.domain.member.exception.TokenTypeNotValidException
import com.dotori.v2.domain.member.presentation.data.res.RefreshResDto
import com.dotori.v2.domain.member.service.RefreshService
import com.dotori.v2.global.config.security.jwt.TokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class RefreshTokenServiceImpl(
    private val memberRepository: MemberRepository,
    private val tokenProvider: TokenProvider,
) : RefreshService {
    override fun execute(refreshToken: String): RefreshResDto {
        if (!tokenProvider.isRefreshToken(refreshToken))
            throw TokenTypeNotValidException()

        val email = tokenProvider.getUserEmail(refreshToken)

        val member = (memberRepository.findByEmail(email)
            ?: throw MemberNotFoundException())

        val newAccessToken = tokenProvider.createAccessToken(member.email, member.roles)
        val newRefreshToken = tokenProvider.createRefreshToken(member.email)
        val expiredTime = tokenProvider.accessExpiredTime

        member.updateRefreshToken(newRefreshToken)

        return RefreshResDto(
            newAccessToken,
            newRefreshToken,
            expiredTime,
            roles = member.roles
        )
    }
}