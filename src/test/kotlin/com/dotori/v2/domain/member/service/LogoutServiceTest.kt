package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.auth.domain.repository.RefreshTokenRepository
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.service.impl.LogoutServiceImpl
import com.dotori.v2.global.util.UserUtil
import com.dotori.v2.testUtil.TestUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class LogoutServiceTest : BehaviorSpec({
    val userUtil = mockk<UserUtil>()
    val refreshTokenRepository = mockk<RefreshTokenRepository>()
    val logoutService = LogoutServiceImpl(userUtil, refreshTokenRepository)
    given("refreshToken이 'testRefreshToken'인 유저가 주어지고") {
        val testMember = Member(
            memberName = "test",
            stuNum = "2116",
            email = "test@gsm.hs.kr",
            gender = "MALE",
            roles = Collections.singletonList(Role.ROLE_MEMBER),
            ruleViolation = mutableListOf(),
            profileImage = null
        )

        every { userUtil.fetchCurrentUser() } returns testMember
        val refreshToken = TestUtils.data().auth().entity(memberId = testMember.id)
        every { refreshTokenRepository.findByMemberId(memberId = testMember.id) } returns refreshToken
        `when`("logoutService를 실행하고") {
            every { refreshTokenRepository.delete(refreshToken) } returns Unit
            logoutService.execute()
            then("그때 delete 쿼리가 한번 실행되어야 함") {
                verify(exactly = 1) { refreshTokenRepository.delete(refreshToken) }
            }
        }
    }
})