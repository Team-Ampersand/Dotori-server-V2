package com.dotori.v2.domain.auth.service.impl

import com.dotori.v2.domain.auth.presentation.data.req.SignUpReqDto
import com.dotori.v2.domain.auth.service.SignUpService
import com.dotori.v2.domain.email.domain.repository.EmailCertificateRepository
import com.dotori.v2.domain.email.exception.EmailAuthNotFoundException
import com.dotori.v2.domain.email.exception.EmailNotBeenException
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.exception.MemberAlreadyException
import com.dotori.v2.domain.student.presentation.data.res.FindAllStudentListResDto
import com.dotori.v2.global.config.redis.service.RedisCacheService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class SignUpServiceImpl(
    private val emailCertificateRepository: EmailCertificateRepository,
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val redisCacheService: RedisCacheService
): SignUpService {

    private val CACHE_KEY = "memberList"

    override fun execute(signUpReqDto: SignUpReqDto) {
        val emailCertificate = emailCertificateRepository.findByEmail(signUpReqDto.email)
            ?: throw EmailAuthNotFoundException()

        if(!emailCertificate.authentication)
            throw EmailNotBeenException()

        emailCertificateRepository.delete(emailCertificate)

        if(memberRepository.existsByEmail(signUpReqDto.email))
            throw MemberAlreadyException()

        val encodedPassword = passwordEncoder.encode(signUpReqDto.password)
        val member = signUpReqDto.toEntity(encodedPassword)

        memberRepository.save(member)
        initCache()
    }

    private fun initCache() {
        val cachedData =
            redisCacheService.getFromCache(CACHE_KEY) as? FindAllStudentListResDto

        if (cachedData != null) {
            redisCacheService.deleteFromCache(CACHE_KEY)
        }
    }
}