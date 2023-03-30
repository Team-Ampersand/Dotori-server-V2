package com.dotori.v2.domain.member.presentation.data.req

class NoAuthNewPasswordReqDto(
    val email: String,
    newPassword: String,
    currentPassword: String,
) : NewPasswordReqDto(currentPassword, newPassword)