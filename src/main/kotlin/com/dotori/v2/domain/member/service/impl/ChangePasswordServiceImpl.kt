package com.dotori.v2.domain.member.service.impl

import com.dotori.v2.domain.email.domain.repository.EmailCertificateRepository
import com.dotori.v2.domain.email.exception.EmailNotBeenException
import com.dotori.v2.domain.email.exception.EmailAuthNotFoundException
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import com.dotori.v2.domain.member.exception.PasswordMismatchException
import com.dotori.v2.domain.member.presentation.data.req.NoAuthNewPasswordReqDto
import com.dotori.v2.domain.member.service.ChangePasswordService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class ChangePasswordServiceImpl(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailCertificateRepository: EmailCertificateRepository,
) : ChangePasswordService {
    override fun execute(newPasswordReqDto: NoAuthNewPasswordReqDto) {
        val emailCertificate = emailCertificateRepository.findByEmail(newPasswordReqDto.email)
            ?: throw EmailAuthNotFoundException()
        if (!emailCertificate.authentication)
            throw EmailNotBeenException()
        val member = (memberRepository.findByEmail(newPasswordReqDto.email)
            ?: throw MemberNotFoundException())
        member.updatePassword(passwordEncoder.encode(newPasswordReqDto.newPassword))
    }
}