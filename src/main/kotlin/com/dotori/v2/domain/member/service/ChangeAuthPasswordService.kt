package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.member.presentation.data.req.NewPasswordReqDto

interface ChangeAuthPasswordService {
    fun execute(newPasswordReqDto: NewPasswordReqDto)
}