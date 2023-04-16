package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.email.domain.entity.EmailCertificate
import com.dotori.v2.domain.email.domain.repository.EmailCertificateRepository
import com.dotori.v2.domain.email.exception.EmailAuthNotFoundException
import com.dotori.v2.domain.email.exception.EmailNotBeenException
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import com.dotori.v2.domain.member.presentation.data.req.NewPasswordReqDto
import com.dotori.v2.domain.member.presentation.data.req.NoAuthNewPasswordReqDto
import com.dotori.v2.domain.member.service.impl.ChangePasswordServiceImpl
import com.dotori.v2.domain.rule.enums.Rule
import com.dotori.v2.global.util.UserUtil
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime
import java.util.*

class ChangePasswordServiceTest : BehaviorSpec({
    val repository = mockk<MemberRepository>()
    val passwordEncoder = mockk<PasswordEncoder>()
    val emailCertificateRepository = mockk<EmailCertificateRepository>()

    val changePasswordServiceImpl = ChangePasswordServiceImpl(repository, passwordEncoder, emailCertificateRepository)
    given("유저가 주어지고"){
        val testMember = Member(
            memberName = "test",
            stuNum = "2116",
            email = "test@gsm.hs.kr",
            password = "test",
            ruleViolation = mutableListOf(),
            gender = Gender.MAN,
            roles = Collections.singletonList(Role.ROLE_MEMBER)
        )
        val emailCertificate = EmailCertificate(1, testMember.email, "testKey", LocalDateTime.now(), true)
        val request = NoAuthNewPasswordReqDto("test@gsm.hs.kr", "1234")
        init(repository, request, testMember, emailCertificateRepository, emailCertificate, passwordEncoder)
        `when`("서비스를 실행하면"){
            changePasswordServiceImpl.execute(request)
            then("패스워드가 변경되어야함"){
                testMember.password shouldBe request.newPassword
            }
        }

        every { emailCertificateRepository.findByEmail(request.email) } returns null
        `when`("메일이 만료됐을때"){
            then("EmailAuthNotFoundException이 발생해야함"){
                val exception = shouldThrow<EmailAuthNotFoundException> {
                    changePasswordServiceImpl.execute(request)
                }
                exception shouldBe EmailAuthNotFoundException()
            }
        }
        init(repository, request, testMember, emailCertificateRepository, emailCertificate, passwordEncoder)

        every { emailCertificateRepository.findByEmail(testMember.email) } returns EmailCertificate(1, testMember.email, "testKey", LocalDateTime.now(), false)
        `when`("메일이 인증되지 않았을때"){
            then("EmailNotBeenException이 발생해야함"){
                val exception = shouldThrow<EmailNotBeenException> {
                    changePasswordServiceImpl.execute(request)
                }
                exception shouldBe EmailNotBeenException()
            }
        }
        init(repository, request, testMember, emailCertificateRepository, emailCertificate, passwordEncoder)

        every { repository.findByEmail(request.email) } returns null
        `when`("해당 메일을 가진 유저가 존재하지 않을때"){
            then("MemberNotFoundException이 발생해야함"){
                val exception = shouldThrow<MemberNotFoundException> {
                    changePasswordServiceImpl.execute(request)
                }
                exception shouldBe MemberNotFoundException()
            }
        }
    }

})

private fun init(
    repository: MemberRepository,
    request: NoAuthNewPasswordReqDto,
    testMember: Member,
    emailCertificateRepository: EmailCertificateRepository,
    emailCertificate: EmailCertificate,
    passwordEncoder: PasswordEncoder
) {
    every { repository.findByEmail(request.email) } returns testMember
    every { emailCertificateRepository.findByEmail(testMember.email) } returns emailCertificate
    every { passwordEncoder.encode(request.newPassword) } returns request.newPassword
}