package com.dotori.v2.domain.selfstudy.service

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.selfstudy.domain.entity.SelfStudyCount
import com.dotori.v2.domain.selfstudy.service.impl.ApplySelfStudyServiceImpl
import com.dotori.v2.domain.selfstudy.util.FindSelfStudyCountUtil
import com.dotori.v2.domain.selfstudy.util.SaveSelfStudyUtil
import com.dotori.v2.domain.selfstudy.util.SelfStudyCheckUtil
import com.dotori.v2.domain.selfstudy.util.ValidDayOfWeekAndHourUtil
import com.dotori.v2.global.util.UserUtil
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.util.*

class ApplySelfStudyServiceSynchronicityTest  : BehaviorSpec({
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

    given("유저가 주어지고") {
        val testMember = Member(
            memberName = "test",
            stuNum = "2116",
            email = "test@gsm.hs.kr",
            gender = Gender.MAN.name,
            roles = Collections.singletonList(Role.ROLE_MEMBER),
            ruleViolation = mutableListOf(),
            profileImage = null
        )

        val selfStudyCount = SelfStudyCount(id = 1, count = 49, 50)

        init(
            validDayOfWeekAndHourUtil,
            userUtil,
            testMember,
            findSelfStudyCountUtil,
            selfStudyCount,
            selfStudyCheckUtil,
            saveSelfStudyUtil
        )

        `when`("서비스를 실행하면") {
            runBlocking {
                val jobs = List(10) {
                    async {
                        runCatching {
                            service.execute()
                        }.onFailure {
                            println("$it")
                        }
                    }
                }
                jobs.forEach { it.await() }
            }

            then("save가 한번만 실행되어야함") {
                verify(exactly = 1) { saveSelfStudyUtil.save(testMember) }
            }

            then("selfStudyCount가 올라가야함") {
                selfStudyCount.count shouldBe 50
            }
            then("유저의 자습신청 상태는 신청된 상태가 되야함") {
                testMember.selfStudyStatus shouldBe SelfStudyStatus.APPLIED
            }
        }
    }
})

private fun init(
    validDayOfWeekAndHourUtil: ValidDayOfWeekAndHourUtil,
    userUtil: UserUtil,
    testMember: Member,
    findSelfStudyCountUtil: FindSelfStudyCountUtil,
    selfStudyCount: SelfStudyCount,
    selfStudyCheckUtil: SelfStudyCheckUtil,
    saveSelfStudyUtil: SaveSelfStudyUtil
) {
    every { validDayOfWeekAndHourUtil.validateApply() } returns Unit
    every { userUtil.fetchCurrentUser() } returns testMember
    every { findSelfStudyCountUtil.findSelfStudyCount() } returns selfStudyCount
    every { selfStudyCheckUtil.isSelfStudyStatusCan(testMember) } returns Unit
    every { saveSelfStudyUtil.save(testMember) } returns Unit
}