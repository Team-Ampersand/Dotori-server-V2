package com.dotori.v2.testUtil

import com.dotori.v2.domain.auth.domain.entity.RefreshToken
import com.dotori.v2.domain.auth.presentation.data.dto.SignInGAuthDto
import com.dotori.v2.domain.auth.presentation.data.req.SignInGAuthReqDto
import com.dotori.v2.domain.auth.presentation.data.res.SignInResDto
import java.time.ZonedDateTime

object AuthDataUtil {
    fun signInRequestDto(code: String) = SignInGAuthReqDto(
        code = code
    )

    fun signInDto(code: String) = SignInGAuthDto(
        code = code
    )

    fun signInResponseDto(
        accessToken: String,
        refreshToken: String,
        accessExp: ZonedDateTime,
        refreshExp: ZonedDateTime
    ) = SignInResDto(
        accessToken = accessToken,
        refreshToken = refreshToken,
        accessExp = accessExp,
        refreshExp = refreshExp
    )

    fun entity(memberId: Long) =
        RefreshToken(
            memberId = memberId,
            token = "thisIsRefreshToken"
        )


}