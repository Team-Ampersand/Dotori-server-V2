package com.dotori.v2.domain.email.service.impl

import com.dotori.v2.domain.email.presentation.dto.request.EmailReqDto
import com.dotori.v2.domain.email.service.EmailSendService
import com.dotori.v2.domain.email.service.SingupEmailSendService
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class SingupEmailSendServiceImpl(
    private val emailSendService: EmailSendService,
    private val memberRepository: MemberRepository
) : SingupEmailSendService {
    override fun execute(emailReqDto: EmailReqDto): String {
        if (memberRepository.existsByEmail(emailReqDto.email))
            throw RuntimeException()
        return emailSendService.execute(emailReqDto)
    }
}