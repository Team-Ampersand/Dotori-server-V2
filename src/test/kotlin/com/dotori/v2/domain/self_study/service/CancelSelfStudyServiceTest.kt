package com.dotori.v2.domain.self_study.service

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.self_study.domain.entity.SelfStudyCount
import com.dotori.v2.domain.self_study.domain.repository.SelfStudyRepository
import com.dotori.v2.domain.self_study.service.impl.CancelSelfStudyServiceImpl
import com.dotori.v2.domain.self_study.util.FindSelfStudyCountUtil
import com.dotori.v2.domain.self_study.util.SelfStudyCheckUtil
import com.dotori.v2.domain.self_study.util.ValidDayOfWeekAndHourUtil
import com.dotori.v2.global.util.UserUtil
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class CancelSelfStudyServiceTest : BehaviorSpec({
    val userUtil = mockk<UserUtil>()
    val findSelfStudyCountUtil = mockk<FindSelfStudyCountUtil>()
    val validDayOfWeekAndHourUtil = mockk<ValidDayOfWeekAndHourUtil>()
    val selfStudyRepository = mockk<SelfStudyRepository>()
    val selfStudyCheckUtil = mockk<SelfStudyCheckUtil>()
    val serviceImpl = CancelSelfStudyServiceImpl(
        userUtil,
        validDayOfWeekAndHourUtil,
        findSelfStudyCountUtil,
        selfStudyRepository,
        selfStudyCheckUtil
    )
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
        val selfStudyCount = SelfStudyCount(id = 1)
        every { validDayOfWeekAndHourUtil.validateCancel() } returns Unit
        every { userUtil.fetchCurrentUser() } returns testMember
        every { findSelfStudyCountUtil.findSelfStudyCount() } returns selfStudyCount
        every { selfStudyCheckUtil.isSelfStudyStatusApplied(testMember) } returns Unit
        every { selfStudyRepository.deleteByMember(testMember) } returns Unit
        `when`("서비스를 실행하면"){
            serviceImpl.execute()
            then("delete 쿼리가 날라가야함"){
                verify(exactly = 1) { selfStudyRepository.deleteByMember(testMember) }
            }
        }
    }
})