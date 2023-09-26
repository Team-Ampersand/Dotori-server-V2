package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.email.domain.entity.EmailCertificate
import com.dotori.v2.domain.email.domain.repository.EmailCertificateRepository
import com.dotori.v2.domain.email.exception.EmailAuthNotFoundException
import com.dotori.v2.domain.email.exception.EmailNotBeenException
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.exception.MemberAlreadyException
import com.dotori.v2.domain.member.presentation.data.req.SignupReqDto
import com.dotori.v2.domain.member.service.impl.SignupServiceImpl
import com.dotori.v2.global.error.ErrorCode
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime
import java.util.*

class SignupServiceTest : BehaviorSpec({
    val emailCertificateRepository = mockk<EmailCertificateRepository>()
    val memberRepository = mockk<MemberRepository>()
    val passwordEncoder = mockk<PasswordEncoder>()
    val signupServiceImpl = SignupServiceImpl(emailCertificateRepository, memberRepository, passwordEncoder)
    given("signupReqDto가 주어지고"){
        val reqDto = SignupReqDto(
            memberName = "test",
            stuNum = "3217",
            password = "password",
            email = "test@gsm.hs.kr",
            gender = Gender.PENDING
        )
        val testMember = Member(
            memberName = "test",
            stuNum = "2116",
            email = "test@gsm.hs.kr",
            password = "test",
            ruleViolation = mutableListOf(),
            gender = Gender.MAN,
            roles = Collections.singletonList(Role.ROLE_MEMBER),
            profileImage = null
        )
        val mockReq = reqToMock(reqDto)
        val emailCertificate = EmailCertificate(mockReq.email, "testKey", LocalDateTime.now(), true)
        initMock(emailCertificateRepository, reqDto, emailCertificate, memberRepository, passwordEncoder, testMember, mockReq)
        `when`("회원가입이 실행될때"){
            val result = signupServiceImpl.execute(mockReq)
            then("save가 실행되어야함"){
                verify(exactly = 1) { memberRepository.save(testMember) }
            }
            then("result는 testMember의 id랑 같아야함"){
                result shouldBe testMember.id
            }
        }

        every { emailCertificateRepository.findByEmail(reqDto.email) } returns null
        `when`("이메일 인증을 찾을 수 없을때"){
            then("EmailAuthNotFoundException이 발생해야됨"){
                val exception = shouldThrow<EmailAuthNotFoundException> {
                    signupServiceImpl.execute(mockReq)
                }
                exception.errorCode shouldBe ErrorCode.MAIL_AUTH_NOT_FOUND
            }
        }
        initMock(emailCertificateRepository, reqDto, emailCertificate, memberRepository, passwordEncoder, testMember, mockReq)

        every { emailCertificateRepository.findByEmail(reqDto.email) } returns EmailCertificate(mockReq.email, "testKey", LocalDateTime.now(), false)
        `when`("이메일이 인증되지 않았을때"){
            then("EmailNotBeenException이 발생해야됨"){
                val exception = shouldThrow<EmailNotBeenException> {
                    signupServiceImpl.execute(mockReq)
                }
                exception.errorCode shouldBe ErrorCode.MEMBER_EMAIL_HAS_NOT_BEEN_CERTIFICATE
            }
        }
        initMock(emailCertificateRepository, reqDto, emailCertificate, memberRepository, passwordEncoder, testMember, mockReq)

        every { memberRepository.existsByEmail(reqDto.email) } returns true
        `when`("이미 유저가 존재할때"){
            then("MemberAlreadyException이 발생해야됨"){
                val exception = shouldThrow<MemberAlreadyException> {
                    signupServiceImpl.execute(mockReq)
                }
                exception.errorCode shouldBe ErrorCode.MEMBER_ALREADY
            }
        }
    }
})

private fun initMock(
    emailCertificateRepository: EmailCertificateRepository,
    req: SignupReqDto,
    emailCertificate: EmailCertificate,
    memberRepository: MemberRepository,
    passwordEncoder: PasswordEncoder,
    testMember: Member,
    mockReq: SignupReqDto
) {
    every { emailCertificateRepository.findByEmail(req.email) } returns emailCertificate
    every { emailCertificateRepository.delete(emailCertificate) } returns Unit
    every { memberRepository.existsByEmail(req.email) } returns false
    every { passwordEncoder.encode(req.password) } returns req.password
    every { memberRepository.save(testMember) } returns testMember
    every { mockReq.toEntity(req.password) } returns testMember
}

private fun reqToMock(signupReqDto: SignupReqDto): SignupReqDto{
    val mockReq = mockk<SignupReqDto>()
    every { mockReq.email } returns signupReqDto.email
    every { mockReq.password } returns signupReqDto.password
    every { mockReq.gender } returns signupReqDto.gender
    every { mockReq.memberName } returns signupReqDto.memberName
    every { mockReq.stuNum } returns signupReqDto.stuNum
    return mockReq
}