package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.service.impl.LogoutServiceImpl
import com.dotori.v2.global.util.UserUtil
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.*

class LogoutServiceTest : BehaviorSpec({
    val userUtil = mockk<UserUtil>()
    val logoutService = LogoutServiceImpl(userUtil)
    given("refreshToken이 'testRefreshToken'인 유저가 주어지고") {
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
        testMember.updateRefreshToken("testRefreshToken")
        every { userUtil.fetchCurrentUser() } returns testMember
        `when`("서비스를 실행하면") {
            logoutService.execute()
            then("유저의 리프레시 토큰은 비어있어야함") {
                testMember.refreshToken shouldBe ""
            }
        }
    }
})