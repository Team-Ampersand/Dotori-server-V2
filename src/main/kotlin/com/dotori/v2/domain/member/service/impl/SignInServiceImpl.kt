package com.dotori.v2.domain.member.service.impl

import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import com.dotori.v2.domain.member.exception.PasswordMismatchException
import com.dotori.v2.domain.member.presentation.dto.req.SignInReqDto
import com.dotori.v2.domain.member.presentation.dto.res.SignInResDto
import com.dotori.v2.domain.member.service.SignInService
import com.dotori.v2.global.config.security.jwt.TokenProvider
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class SignInServiceImpl(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenProvider: TokenProvider,
) : SignInService {
    override fun execute(signInReqDto: SignInReqDto): SignInResDto {
        val member = memberRepository.findByEmail(signInReqDto.email)
            ?: throw MemberNotFoundException()
        if(!passwordEncoder.matches(signInReqDto.password, member.password))
            throw PasswordMismatchException()// 패스워드 일치 X
        val accessToken = tokenProvider.createAccessToken(member.email, member.roles)
        val refreshToken = tokenProvider.createRefreshToken(member.email)
        val accessExpiredTime = tokenProvider.accessExpiredTime
        return SignInResDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresAt = accessExpiredTime
        )
    }
}