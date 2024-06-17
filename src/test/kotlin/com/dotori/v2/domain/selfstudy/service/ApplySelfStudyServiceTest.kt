package com.dotori.v2.domain.selfstudy.service

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.selfstudy.domain.entity.SelfStudyCount
import com.dotori.v2.domain.selfstudy.exception.AlreadyApplySelfStudyException
import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyApplyDayException
import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyApplyHourException
import com.dotori.v2.domain.selfstudy.exception.SelfStudyOverException
import com.dotori.v2.domain.selfstudy.service.impl.ApplySelfStudyServiceImpl
import com.dotori.v2.domain.selfstudy.util.FindSelfStudyCountUtil
import com.dotori.v2.domain.selfstudy.util.SaveSelfStudyUtil
import com.dotori.v2.domain.selfstudy.util.SelfStudyCheckUtil
import com.dotori.v2.domain.selfstudy.util.ValidDayOfWeekAndHourUtil
import com.dotori.v2.global.config.redis.service.RedisCacheService
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
    val redisCacheService = mockk<RedisCacheService>()

    val service = ApplySelfStudyServiceImpl(
        userUtil,
        findSelfStudyCountUtil,
        selfStudyCheckUtil,
        saveSelfStudyUtil,
        validDayOfWeekAndHourUtil,
        redisCacheService
    )
    given("유저가 주어지고") {
        val testMember = Member(
            memberName = "test",
            stuNum = "2116",
            email = "test@gsm.hs.kr",
            password = "password1234",
            gender = Gender.MAN,
            roles = Collections.singletonList(Role.ROLE_MEMBER),
            ruleViolation = mutableListOf(),
            profileImage = null
        )
        val selfStudyCount = SelfStudyCount(id = 1)
        init(validDayOfWeekAndHourUtil, userUtil, testMember, findSelfStudyCountUtil, selfStudyCount, selfStudyCheckUtil, saveSelfStudyUtil, redisCacheService)
        `when`("서비스를 실행하면") {
            service.execute()
            then("save가 실행되어야함") {
                verify(exactly = 1) { saveSelfStudyUtil.save(testMember) }
            }
            then("selfStudyCount가 올라가야함") {
                selfStudyCount.count shouldBe 1
            }
            then("유저의 자습신청 상태는 신청된 상태가 되야함"){
                testMember.selfStudyStatus shouldBe SelfStudyStatus.APPLIED
            }
        }

        every { findSelfStudyCountUtil.findSelfStudyCount() } returns SelfStudyCount(id = 1, count = 50)
        `when`("신청자가 50명 이상일때") {
            then("SelfStudyOverException이 터져야함") {
                shouldThrow<SelfStudyOverException> {
                    service.execute()
                }
            }
        }
        init(validDayOfWeekAndHourUtil, userUtil, testMember, findSelfStudyCountUtil, selfStudyCount, selfStudyCheckUtil, saveSelfStudyUtil, redisCacheService)

        every { validDayOfWeekAndHourUtil.validateApply() } throws NotSelfStudyApplyDayException()
        `when`("신청할 수 없는 요일일때") {
            then("NotSelfStudyApplyDayException이 발생해야함") {
                shouldThrow<NotSelfStudyApplyDayException> {
                    service.execute()
                }
            }
        }
        init(validDayOfWeekAndHourUtil, userUtil, testMember, findSelfStudyCountUtil, selfStudyCount, selfStudyCheckUtil, saveSelfStudyUtil, redisCacheService)

        every { validDayOfWeekAndHourUtil.validateApply() } throws NotSelfStudyApplyHourException()
        `when`("신청할 수 없는 시간알때") {
            then("NotSelfStudyApplyHourException이 발생해야함") {
                shouldThrow<NotSelfStudyApplyHourException> {
                    service.execute()
                }
            }
        }
        init(validDayOfWeekAndHourUtil, userUtil, testMember, findSelfStudyCountUtil, selfStudyCount, selfStudyCheckUtil, saveSelfStudyUtil, redisCacheService)

        every { selfStudyCheckUtil.isSelfStudyStatusCan(testMember) } throws AlreadyApplySelfStudyException()
        `when`("이미 신청한 유저일때") {
            then("AlreadyApplySelfStudyException이 발생해야함") {
                shouldThrow<AlreadyApplySelfStudyException> {
                    service.execute()
                }
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
    saveSelfStudyUtil: SaveSelfStudyUtil,
    redisCacheService: RedisCacheService
) {
    every { validDayOfWeekAndHourUtil.validateApply() } returns Unit
    every { userUtil.fetchCurrentUser() } returns testMember
    every { findSelfStudyCountUtil.findSelfStudyCount() } returns selfStudyCount
    every { selfStudyCheckUtil.isSelfStudyStatusCan(testMember) } returns Unit
    every { saveSelfStudyUtil.save(testMember) } returns Unit
    every { redisCacheService.updateCacheFromSelfStudy(any(), any()) } returns Unit
}