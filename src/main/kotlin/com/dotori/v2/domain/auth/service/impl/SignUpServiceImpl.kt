package com.dotori.v2.domain.auth.service.impl

import com.dotori.v2.domain.auth.presentation.data.req.SignUpReqDto
import com.dotori.v2.domain.auth.service.SignUpService
import com.dotori.v2.domain.email.domain.repository.EmailCertificateRepository
import com.dotori.v2.domain.email.exception.EmailAuthNotFoundException
import com.dotori.v2.domain.email.exception.EmailNotBeenException
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.exception.MemberAlreadyException
import com.dotori.v2.domain.student.presentation.data.res.FindAllStudentResDto
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

        updateCache(member)
    }

    private fun updateCache(member: Member) {
        val cacheKey = "memberList"
        val cachedData = redisCacheService.getFromCache(cacheKey) as? List<FindAllStudentResDto>

        if (cachedData != null) {
            val updatedList = cachedData.toMutableList().apply {
                add(
                    FindAllStudentResDto(
                        id = member.id,
                        gender = member.gender,
                        memberName = member.memberName,
                        stuNum = member.stuNum,
                        role = Role.ROLE_MEMBER,
                        selfStudyStatus = member.selfStudyStatus,
                        profileImage = null
                    )
                )
            }
            redisCacheService.putToCache(cacheKey, updatedList)
        }
    }
}