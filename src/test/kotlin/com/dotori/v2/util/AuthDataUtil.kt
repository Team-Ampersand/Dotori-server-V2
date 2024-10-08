package com.dotori.v2.util

import com.dotori.v2.domain.auth.domain.entity.RefreshToken
import com.dotori.v2.domain.auth.presentation.data.res.SignInResDto
import com.dotori.v2.domain.member.enums.Role
import java.time.ZonedDateTime

object AuthDataUtil {

    fun signInResponseDto(
        accessToken: String,
        refreshToken: String,
        accessExp: ZonedDateTime,
        refreshExp: ZonedDateTime,
        roles: MutableList<Role>,
        expiresAt: ZonedDateTime
    ) = SignInResDto(
        accessToken = accessToken,
        refreshToken = refreshToken,
        accessExp = accessExp,
        refreshExp = refreshExp,
        roles = roles,
        expiresAt = expiresAt
    )

    fun entity(memberId: Long) =
        RefreshToken(
            memberId = memberId,
            token = "thisIsRefreshToken"
        )


}