package com.dotori.v2.domain.member.service.impl

import com.dotori.v2.domain.auth.domain.entity.RefreshToken
import com.dotori.v2.domain.auth.domain.repository.RefreshTokenRepository
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import com.dotori.v2.domain.member.service.LogoutService
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class LogoutServiceImpl(
    private val userUtil: UserUtil,
    private val refreshTokenRepository: RefreshTokenRepository
) : LogoutService{
    override fun execute() {
        val member: Member = userUtil.fetchCurrentUser()

        val refreshToken: RefreshToken = refreshTokenRepository.findByMemberId(member.id)
            ?: throw MemberNotFoundException()

        refreshTokenRepository.delete(refreshToken)
    }

}