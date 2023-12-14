package com.dotori.v2.domain.auth.presentation.data.dto

@Deprecated("GAuth 로그인  변경 때문입니다.")
data class SignInEmailAndPasswordDto(
    val email: String,
    val password: String
)
