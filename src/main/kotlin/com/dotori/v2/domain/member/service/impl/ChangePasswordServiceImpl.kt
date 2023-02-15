package com.dotori.v2.domain.member.service.impl

import com.dotori.v2.domain.email.domain.repository.EmailCertificateRepository
import com.dotori.v2.domain.email.exception.EmailNotBeenException
import com.dotori.v2.domain.email.exception.EmailSendFailException
import com.dotori.v2.domain.member.exception.PasswordMismatchException
import com.dotori.v2.domain.member.presentation.dto.req.NewPasswordReqDto
import com.dotori.v2.domain.member.service.ChangePasswordService
import com.dotori.v2.global.util.UserUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class ChangePasswordServiceImpl(
    private val userUtil: UserUtil,
    private val passwordEncoder: PasswordEncoder,
    private val emailCertificateRepository: EmailCertificateRepository,
) : ChangePasswordService {
    override fun execute(newPasswordReqDto: NewPasswordReqDto) {
        val member = userUtil.fetchCurrentUser()
        val emailCertificate = emailCertificateRepository.findByEmail(member.email)
            ?: throw EmailSendFailException()
        if (!emailCertificate.authentication)
            throw EmailNotBeenException()
        if (!passwordEncoder.matches(newPasswordReqDto.currentPassword, member.password))
            throw PasswordMismatchException()
        member.updatePassword(passwordEncoder.encode(newPasswordReqDto.newPassword))
    }
}