package com.dotori.v2.domain.auth.presentation.data.res

import com.dotori.v2.domain.member.enums.Role
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.ZonedDateTime

data class SignInResDto(
    val accessToken: String,
    val refreshToken: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val accessExp: ZonedDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val refreshExp: ZonedDateTime,
    val roles: MutableList<Role>
)