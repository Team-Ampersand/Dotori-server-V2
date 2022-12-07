package com.dotori.v2.domain.email.service

import com.dotori.v2.domain.email.presentation.dto.request.EmailReqDto

interface PasswordChangeEmailSendService {
    fun execute(emailReqDto: EmailReqDto): String
}