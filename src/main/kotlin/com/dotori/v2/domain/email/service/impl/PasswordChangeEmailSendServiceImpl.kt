package com.dotori.v2.domain.email.service.impl

import com.dotori.v2.domain.email.presentation.dto.request.EmailReqDto
import com.dotori.v2.domain.email.service.EmailSendService
import com.dotori.v2.domain.email.service.PasswordChangeEmailSendService
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import org.springframework.stereotype.Service

@Service
class PasswordChangeEmailSendServiceImpl(
    private val memberRepository: MemberRepository,
    private val emailSendService: EmailSendService
) : PasswordChangeEmailSendService {
    override fun execute(emailReqDto: EmailReqDto) {
        if (!memberRepository.existsByEmail(emailReqDto.email))
            throw MemberNotFoundException()

        emailSendService.execute(emailReqDto)
    }
}