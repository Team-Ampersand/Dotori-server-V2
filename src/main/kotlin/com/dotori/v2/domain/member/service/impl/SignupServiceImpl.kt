package com.dotori.v2.domain.member.service.impl

import com.dotori.v2.domain.email.domain.repository.EmailCertificateRepository
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.email.exception.EmailNotBeenException
import com.dotori.v2.domain.email.exception.EmailSendFailException
import com.dotori.v2.domain.member.exception.MemberAlreadyException
import com.dotori.v2.domain.member.presentation.data.req.SignupReqDto
import com.dotori.v2.domain.member.service.SignupService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class SignupServiceImpl(
    private val emailCertificateRepository: EmailCertificateRepository,
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
): SignupService {
    override fun execute(signupReqDto: SignupReqDto): Long {
        val emailCertificate = emailCertificateRepository.findByEmail(signupReqDto.email)
            ?: throw EmailSendFailException()
        if(!emailCertificate.authentication)
            throw EmailNotBeenException()
        emailCertificateRepository.delete(emailCertificate)
        if(memberRepository.existsByEmail(signupReqDto.email))
            throw MemberAlreadyException()
        val encodedPassword = passwordEncoder.encode(signupReqDto.password)
        val member = signupReqDto.toEntity(encodedPassword)
        return memberRepository.save(member).id
    }
}
