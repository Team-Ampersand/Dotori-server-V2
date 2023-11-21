package com.dotori.v2.testUtil

import com.dotori.v2.domain.auth.domain.entity.RefreshToken
import com.dotori.v2.domain.member.presentation.data.dto.SignInDto
import com.dotori.v2.domain.member.presentation.data.req.SignInReqDto
import com.dotori.v2.domain.member.presentation.data.res.SignInResDto
import java.time.ZonedDateTime
import java.util.*

object AuthDataUtil {
    fun signInRequestDto(code: String) = SignInReqDto(
        code = code
    )

    fun signInDto(code: String) = SignInDto(
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

    fun entity(memberId: UUID) =
        RefreshToken(
            memberId = memberId,
            token = "thisIsRefreshToken"
        )


}