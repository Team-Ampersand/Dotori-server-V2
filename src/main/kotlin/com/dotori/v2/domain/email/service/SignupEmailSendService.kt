package com.dotori.v2.domain.email.service

import com.dotori.v2.domain.email.presentation.dto.request.EmailReqDto

interface SignupEmailSendService {
    fun execute(emailReqDto: EmailReqDto): String
}