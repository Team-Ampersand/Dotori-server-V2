package com.dotori.v2.domain.selfstudy.service

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.selfstudy.domain.entity.SelfStudyCount
import com.dotori.v2.domain.selfstudy.domain.repository.SelfStudyRepository
import com.dotori.v2.domain.selfstudy.exception.NotAppliedException
import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyCancelDayException
import com.dotori.v2.domain.selfstudy.exception.NotSelfStudyCancelHourException
import com.dotori.v2.domain.selfstudy.service.impl.CancelSelfStudyServiceImpl
import com.dotori.v2.domain.selfstudy.util.FindSelfStudyCountUtil
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

class CancelSelfStudyServiceTest : BehaviorSpec({
    val userUtil = mockk<UserUtil>()
    val findSelfStudyCountUtil = mockk<FindSelfStudyCountUtil>()
    val validDayOfWeekAndHourUtil = mockk<ValidDayOfWeekAndHourUtil>()
    val selfStudyRepository = mockk<SelfStudyRepository>()
    val selfStudyCheckUtil = mockk<SelfStudyCheckUtil>()
    val redisCacheService = mockk<RedisCacheService>()
    val serviceImpl = CancelSelfStudyServiceImpl(
        userUtil,
        validDayOfWeekAndHourUtil,
        findSelfStudyCountUtil,
        selfStudyRepository,
        selfStudyCheckUtil,
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
        val selfStudyCount = SelfStudyCount(id = 1, count = 1)
        init(validDayOfWeekAndHourUtil, userUtil, testMember, findSelfStudyCountUtil, selfStudyCount, selfStudyCheckUtil, selfStudyRepository, redisCacheService)
        `when`("서비스를 실행하면") {
            serviceImpl.execute()
            then("delete 쿼리가 날라가야함") {
                verify(exactly = 1) { selfStudyRepository.deleteByMember(testMember) }
            }
            then("자습 상태가 CANT로 변경") {
                testMember.selfStudyStatus shouldBe SelfStudyStatus.CANT
            }
            then("카운트가 낮아저야됨") {
                selfStudyCount.count shouldBe 0
            }
        }

        every { validDayOfWeekAndHourUtil.validateCancel() } throws NotSelfStudyCancelDayException()
        `when`("취소를 할 수 없는 요일일때") {
            then("NotSelfStudyCancelDayException이 발생해야함") {
                shouldThrow<NotSelfStudyCancelDayException> {
                    serviceImpl.execute()
                }
            }
        }
        init(validDayOfWeekAndHourUtil, userUtil, testMember, findSelfStudyCountUtil, selfStudyCount, selfStudyCheckUtil, selfStudyRepository, redisCacheService)

        every { validDayOfWeekAndHourUtil.validateCancel() } throws NotSelfStudyCancelHourException()
        `when`("취소를 할 수 없는 시간일때") {
            then("NotSelfStudyCancelHourException이 발생해야함") {
                shouldThrow<NotSelfStudyCancelHourException> {
                    serviceImpl.execute()
                }
            }
        }
        init(validDayOfWeekAndHourUtil, userUtil, testMember, findSelfStudyCountUtil, selfStudyCount, selfStudyCheckUtil, selfStudyRepository, redisCacheService)

        every { selfStudyCheckUtil.isSelfStudyStatusApplied(testMember) } throws NotAppliedException()
        `when`("자습에 신청하지 않았을때") {
            then("NotAppliedException이 발생해야함") {
                shouldThrow<NotAppliedException> {
                    serviceImpl.execute()
                }
            }
        }
        init(validDayOfWeekAndHourUtil, userUtil, testMember, findSelfStudyCountUtil, selfStudyCount, selfStudyCheckUtil, selfStudyRepository, redisCacheService)


    }
})

private fun init(
    validDayOfWeekAndHourUtil: ValidDayOfWeekAndHourUtil,
    userUtil: UserUtil,
    testMember: Member,
    findSelfStudyCountUtil: FindSelfStudyCountUtil,
    selfStudyCount: SelfStudyCount,
    selfStudyCheckUtil: SelfStudyCheckUtil,
    selfStudyRepository: SelfStudyRepository,
    redisCacheService: RedisCacheService
) {
    every { validDayOfWeekAndHourUtil.validateCancel() } returns Unit
    every { userUtil.fetchCurrentUser() } returns testMember
    every { findSelfStudyCountUtil.findSelfStudyCount() } returns selfStudyCount
    every { selfStudyCheckUtil.isSelfStudyStatusApplied(testMember) } returns Unit
    every { selfStudyRepository.deleteByMember(testMember) } returns Unit
    every { redisCacheService.updateCacheFromSelfStudy(any(), any()) } returns Unit
}