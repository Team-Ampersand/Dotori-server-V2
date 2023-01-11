package com.dotori.v2.domain.member.service.impl

import com.dotori.v2.domain.email.domain.repository.EmailCertificateRepository
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.presentation.dto.SignupReqDto
import com.dotori.v2.domain.member.service.SignupService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException

@Service
@Transactional(rollbackFor = [Exception::class])
class SignupServiceImpl (
    private val emailCertificateRepository: EmailCertificateRepository,
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
): SignupService{
    override fun execute(signupReqDto: SignupReqDto):Long {
        val emailCertificate = emailCertificateRepository.findByEmail(signupReqDto.email)
            ?: throw RuntimeException() // TODO 인증코드 발송 X
        if(!emailCertificate.authentication)
            throw RuntimeException() // TODO 나중에 예외처리 해야됨 -> 인증된 메일X
        emailCertificateRepository.delete(emailCertificate)
        if(memberRepository.existsByEmail(signupReqDto.email))
            throw RuntimeException() // TODO 나중에 예외처리 해야됨 -> 이미 존재하는 유저
        val encodedPassword = passwordEncoder.encode(signupReqDto.password)
        val member = signupReqDto.toEntity(encodedPassword)
        return memberRepository.save(member).id
    }
}