package com.dotori.v2.domain.auth.util.impl

import com.dotori.v2.domain.auth.domain.entity.RefreshToken
import com.dotori.v2.domain.auth.domain.repository.RefreshTokenRepository
import com.dotori.v2.domain.auth.util.AuthConverter
import com.dotori.v2.domain.auth.util.AuthUtil
import com.dotori.v2.domain.member.domain.entity.Member
import org.springframework.stereotype.Component

@Component
class AuthUtilImpl(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val authConverter: AuthConverter,
) : AuthUtil {

    override fun saveNewRefreshToken(memberInfo: Member, refreshToken: String): RefreshToken {
        return authConverter.toEntity(memberInfo, refreshToken)
            .let { refreshTokenRepository.save(it) }
    }
}