package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.member.presentation.dto.req.NewPasswordReqDto

interface ChangePasswordService {
    fun execute(newPasswordReqDto: NewPasswordReqDto)
}