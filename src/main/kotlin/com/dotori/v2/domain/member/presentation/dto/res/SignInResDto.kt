package com.dotori.v2.domain.member.presentation.dto.res

import java.time.ZonedDateTime

data class SignInResDto(
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: ZonedDateTime,
)