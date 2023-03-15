package com.dotori.v2.domain.self_study.service

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.self_study.domain.entity.SelfStudyCount
import com.dotori.v2.domain.self_study.excetpion.SelfStudyOverException
import com.dotori.v2.domain.self_study.service.impl.ApplySelfStudyServiceImpl
import com.dotori.v2.domain.self_study.util.FindSelfStudyCountUtil
import com.dotori.v2.domain.self_study.util.SaveSelfStudyUtil
import com.dotori.v2.domain.self_study.util.SelfStudyCheckUtil
import com.dotori.v2.domain.self_study.util.ValidDayOfWeekAndHourUtil
import com.dotori.v2.global.util.UserUtil
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class ApplySelfStudyServiceTest : BehaviorSpec({
    val userUtil = mockk<UserUtil>()
    val findSelfStudyCountUtil = mockk<FindSelfStudyCountUtil>()
    val selfStudyCheckUtil = mockk<SelfStudyCheckUtil>()
    val saveSelfStudyUtil = mockk<SaveSelfStudyUtil>()
    val validDayOfWeekAndHourUtil = mockk<ValidDayOfWeekAndHourUtil>()

    val service = ApplySelfStudyServiceImpl(
        userUtil,
        findSelfStudyCountUtil,
        selfStudyCheckUtil,
        saveSelfStudyUtil,
        validDayOfWeekAndHourUtil
    )
    given("유저가 주어지고"){
        val testMember = Member(
            memberName = "test",
            stuNum = "2116",
            email = "test@gsm.hs.kr",
            password = "test",
            gender = Gender.MAN,
            roles = Collections.singletonList(Role.ROLE_MEMBER),
            ruleViolation = mutableListOf()
        )
        val selfStudyCount = SelfStudyCount(id = 1)
        every { validDayOfWeekAndHourUtil.validateApply() } returns Unit
        every { userUtil.fetchCurrentUser() } returns testMember
        every { findSelfStudyCountUtil.findSelfStudyCount() } returns selfStudyCount
        every { selfStudyCheckUtil.isSelfStudyStatusCan(testMember) } returns Unit
        every { saveSelfStudyUtil.save(testMember) } returns Unit
        `when`("서비스를 실행하면"){
            service.execute()
            then("save가 실행되어야함"){
                verify(exactly = 1) { saveSelfStudyUtil.save(testMember) }
            }
            then("selfStudyCount가 올라가야함"){
                selfStudyCount.count shouldBe 1
            }
        }
        `when`("신청자가 50명 이상일때"){
            for (i : Int in 1..50){
                selfStudyCount.addCount()
            }
            then("SelfStudyOverException이 터져야함"){
                shouldThrow<SelfStudyOverException> { service.execute() }
            }
        }
    }
})