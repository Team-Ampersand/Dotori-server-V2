package com.dotori.v2.domain.main_page.service

import com.dotori.v2.domain.main_page.presentation.dto.res.PersonalInfoResDto
import com.dotori.v2.domain.main_page.service.impl.GetPersonalInfoServiceImpl
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.global.util.UserUtil
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.*

class GetPersonalInfoServiceTest : BehaviorSpec({
    val userUtil = mockk<UserUtil>()
    val getPersonalInfoServiceImpl = GetPersonalInfoServiceImpl(userUtil)

    given("유저가 주어지고") {
        val testMember = Member(
            memberName = "test",
            stuNum = "2116",
            email = "test@gsm.hs.kr",
            password = "test",
            gender = Gender.MAN,
            roles = Collections.singletonList(Role.ROLE_MEMBER),
            ruleViolation = mutableListOf()
        )
        every { userUtil.fetchCurrentUser() } returns testMember
        `when`("서비스를 실행하면") {
            val result = getPersonalInfoServiceImpl.execute()
            then("유저의 정보가 dto로 반환되어야한다") {
                result shouldBe PersonalInfoResDto(testMember)
            }
        }
    }
})