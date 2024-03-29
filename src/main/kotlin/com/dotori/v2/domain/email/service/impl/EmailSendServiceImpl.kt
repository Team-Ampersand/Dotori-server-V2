package com.dotori.v2.domain.email.service.impl

import com.dotori.v2.domain.email.domain.entity.EmailCertificate
import com.dotori.v2.domain.email.domain.repository.EmailCertificateRepository
import com.dotori.v2.domain.email.presentation.dto.request.EmailReqDto
import com.dotori.v2.domain.email.service.EmailSendService
import com.dotori.v2.global.email.EmailSender
import com.dotori.v2.global.util.KeyUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(rollbackFor = [Exception::class])
class EmailSendServiceImpl(
    private val emailCertificateRepository: EmailCertificateRepository,
    private val keyUtil: KeyUtil,
    private val emailSender: EmailSender,
) : EmailSendService {

    override fun execute(emailReqDto: EmailReqDto) {
        val key = keyUtil.keyIssuance()
        emailSender.send(emailReqDto.email, key)

        if (emailCertificateRepository.existsByEmail(emailReqDto.email))
            emailCertificateRepository.deleteByEmail(emailReqDto.email)

        val emailCertificate = EmailCertificate(
            email = emailReqDto.email,
            key = key,
            expiredTime = LocalDateTime.now().plusMinutes(5),
            authentication = false
        )

        emailCertificateRepository.save(emailCertificate)
    }
}