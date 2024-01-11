package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.member.presentation.data.req.NoAuthNewPasswordReqDto

interface ChangePasswordService {
    fun execute(newPasswordReqDto: NoAuthNewPasswordReqDto)
}