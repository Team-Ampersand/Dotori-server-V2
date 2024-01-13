package com.dotori.v2.domain.email.service.impl

import com.dotori.v2.domain.email.presentation.dto.request.EmailReqDto
import com.dotori.v2.domain.email.service.EmailSendService
import com.dotori.v2.domain.email.service.SignupEmailSendService
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.exception.MemberAlreadyException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class SignupEmailSendServiceImpl(
    private val emailSendService: EmailSendService,
    private val memberRepository: MemberRepository
) : SignupEmailSendService {
    override fun execute(emailReqDto: EmailReqDto) {
        if (memberRepository.existsByEmail(emailReqDto.email))
            throw MemberAlreadyException()

        emailSendService.execute(emailReqDto)
    }
}