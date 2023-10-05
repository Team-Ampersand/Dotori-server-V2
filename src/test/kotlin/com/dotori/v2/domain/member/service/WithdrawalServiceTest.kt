package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.presentation.data.req.WithdrawalReqDto
import com.dotori.v2.domain.member.service.impl.WithdrawalServiceImpl
import com.dotori.v2.global.thirdparty.aws.s3.S3Service
import com.dotori.v2.global.util.UserUtil
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class WithdrawalServiceTest : BehaviorSpec({
    val memberRepository = mockk<MemberRepository>()
    val userUtil = mockk<UserUtil>()
    val s3Service = mockk<S3Service>()
    val withdrawalServiceImpl = WithdrawalServiceImpl(memberRepository, s3Service, userUtil)
    given("유저와 dto가 주어지고") {
        val testMember = Member(
            memberName = "test",
            stuNum = "2116",
            email = "test@gsm.hs.kr",
            password = "test",
            gender = Gender.MAN,
            roles = Collections.singletonList(Role.ROLE_MEMBER),
            ruleViolation = mutableListOf(),
            profileImage = null
        )
        val request = WithdrawalReqDto("test@gsm.hs.kr", "1234")
        init(userUtil, testMember, memberRepository, request)
        `when`("서비스를 실행하면") {
            withdrawalServiceImpl.execute()
            then("delete가 실행되어야함") {
                verify(exactly = 1) { memberRepository.delete(testMember) }
            }
        }
    }
})

private fun init(
    userUtil: UserUtil,
    testMember: Member,
    memberRepository: MemberRepository,
    request: WithdrawalReqDto,
) {
    every { userUtil.fetchCurrentUser() } returns testMember
    every { memberRepository.findByEmail(request.email) } returns testMember
    every { memberRepository.delete(testMember) } returns Unit
}