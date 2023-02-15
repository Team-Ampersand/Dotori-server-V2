package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.email.domain.entity.EmailCertificate
import com.dotori.v2.domain.email.domain.repository.EmailCertificateRepository
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.presentation.dto.req.NewPasswordReqDto
import com.dotori.v2.domain.member.service.impl.ChangePasswordServiceImpl
import com.dotori.v2.global.util.UserUtil
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime
import java.util.*

class ChangePasswordServiceTest : BehaviorSpec({
    val userUtil = mockk<UserUtil>()
    val passwordEncoder = mockk<PasswordEncoder>()
    val emailCertificateRepository = mockk<EmailCertificateRepository>()

    val changePasswordServiceImpl = ChangePasswordServiceImpl(userUtil, passwordEncoder, emailCertificateRepository)
    given("유저가 주어지고"){
        val testMember = Member(
            memberName = "test",
            stuNum = "2116",
            email = "test@gsm.hs.kr",
            password = "test",
            gender = Gender.MAN,
            roles = Collections.singletonList(Role.ROLE_MEMBER)
        )
        val emailCertificate = EmailCertificate(1, testMember.email, "testKey", LocalDateTime.now(), true)
        val request = NewPasswordReqDto(testMember.password, "1234")
        every { userUtil.fetchCurrentUser() } returns testMember
        every { emailCertificateRepository.findByEmail(testMember.email) } returns emailCertificate
        every { passwordEncoder.matches(request.currentPassword, testMember.password) } returns true
        every { passwordEncoder.encode(request.newPassword) } returns request.newPassword
        `when`("서비스를 실행하면"){
            changePasswordServiceImpl.execute(request)
            then("패스워드가 변경되어야함"){
                testMember.password shouldBe request.newPassword
            }
        }
    }

})