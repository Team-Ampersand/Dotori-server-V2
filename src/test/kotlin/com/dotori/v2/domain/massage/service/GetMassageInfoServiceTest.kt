package com.dotori.v2.domain.massage.service

import com.dotori.v2.domain.massage.domain.entity.MassageCount
import com.dotori.v2.domain.massage.domain.repository.MassageCountRepository
import com.dotori.v2.domain.massage.exception.NotMassageApplyDayException
import com.dotori.v2.domain.massage.exception.NotMassageApplyHourException
import com.dotori.v2.domain.massage.presentation.dto.res.MassageInfoResDto
import com.dotori.v2.domain.massage.service.impl.GetMassageInfoServiceImpl
import com.dotori.v2.domain.massage.util.MassageUtil
import com.dotori.v2.domain.massage.util.ValidDayOfWeekAndHourMassageUtil
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.MassageStatus
import com.dotori.v2.domain.member.util.MemberUtil
import com.dotori.v2.global.util.UserUtil
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class GetMassageInfoServiceTest : BehaviorSpec({
    val massageCountRepository = mockk<MassageCountRepository>()
    val validDayOfWeekAndHourMassageUtil = mockk<ValidDayOfWeekAndHourMassageUtil>()
    val userUtil = mockk<UserUtil>()

    given("서비스가 주어지고") {
        val getMassageInfoServiceImpl = GetMassageInfoServiceImpl(massageCountRepository, validDayOfWeekAndHourMassageUtil, userUtil)

        val massageCount = MassageUtil.createMassageCount()
        val member = MemberUtil.createMember()

        init(massageCountRepository, massageCount, userUtil, member, validDayOfWeekAndHourMassageUtil)
        `when`("서비스를 실행할때") {
            val result = getMassageInfoServiceImpl.execute()
            then("massageInfoResDto가 반환되어야함") {
                result shouldBe MassageInfoResDto(massageCount.count, member.massageStatus, massageCount.limit)
            }
        }

        every { validDayOfWeekAndHourMassageUtil.validateApply() } throws NotMassageApplyDayException()
        `when`("신청할 수 없는 요일일때") {
            val result = getMassageInfoServiceImpl.execute()
            then("massageStatus가 CANT여야함") {
                result.massageStatus shouldBe MassageStatus.CANT
            }
        }
        init(massageCountRepository, massageCount, userUtil, member, validDayOfWeekAndHourMassageUtil)

        every { validDayOfWeekAndHourMassageUtil.validateApply() } throws NotMassageApplyHourException()
        `when`("신청할 수 없는 시간일때") {
            val result = getMassageInfoServiceImpl.execute()
            then("massageStatus가 CANT여야함") {
                result.massageStatus shouldBe MassageStatus.CANT
            }
        }
        init(massageCountRepository, massageCount, userUtil, member, validDayOfWeekAndHourMassageUtil)

        every { massageCountRepository.findMassageCountById(1L) } returns MassageUtil.createMassageCount(limit = 0)
        `when`("안마의자 신청수가 최대인원일때") {
            val result = getMassageInfoServiceImpl.execute()
            then("massageStatus가 CANT여야함") {
                result.massageStatus shouldBe MassageStatus.CANT
            }
        }
        init(massageCountRepository, massageCount, userUtil, member, validDayOfWeekAndHourMassageUtil)

        member.updateMassageStatus(MassageStatus.APPLIED)
        `when`("유저의 안마의자 신청 상태가 CAN이 아닐때") {
            val result = getMassageInfoServiceImpl.execute()
            then("해당 유저의 상태를 담고 있어야함") {
                result.massageStatus shouldBe MassageStatus.APPLIED
            }
        }
    }
})

private fun init(
    massageCountRepository: MassageCountRepository,
    massageCount: MassageCount,
    userUtil: UserUtil,
    member: Member,
    validDayOfWeekAndHourMassageUtil: ValidDayOfWeekAndHourMassageUtil,
) {
    every { massageCountRepository.findMassageCountById(1L) } returns massageCount
    every { userUtil.fetchCurrentUser() } returns member
    every { validDayOfWeekAndHourMassageUtil.validateApply() } returns Unit
}