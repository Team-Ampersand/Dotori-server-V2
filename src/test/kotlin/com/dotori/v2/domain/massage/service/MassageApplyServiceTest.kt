package com.dotori.v2.domain.massage.service

import com.dotori.v2.domain.massage.domain.entity.Massage
import com.dotori.v2.domain.massage.domain.entity.MassageCount
import com.dotori.v2.domain.massage.exception.MassageOverException
import com.dotori.v2.domain.massage.exception.NotMassageApplyDayException
import com.dotori.v2.domain.massage.exception.NotMassageApplyHourException
import com.dotori.v2.domain.massage.service.impl.ApplyMassageServiceImpl
import com.dotori.v2.domain.massage.util.*
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.MassageStatus
import com.dotori.v2.domain.member.util.MemberUtil
import com.dotori.v2.domain.selfstudy.exception.AlreadyApplySelfStudyException
import com.dotori.v2.global.util.UserUtil
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class MassageApplyServiceTest : BehaviorSpec({
    val userUtil = mockk<UserUtil>()
    val validDayOfWeekAndHourMassageUtil = mockk<ValidDayOfWeekAndHourMassageUtil>()
    val findMassageCountUtil = mockk<FindMassageCountUtil>()
    val saveMassageUtil = mockk<SaveMassageUtil>()
    val massageCheckUtil = mockk<MassageCheckUtil>()

    val applyMassageServiceImpl = ApplyMassageServiceImpl(
        userUtil,
        validDayOfWeekAndHourMassageUtil,
        findMassageCountUtil,
        saveMassageUtil,
        massageCheckUtil
    )

    given("유저와 안마의자가 주어지고") {
        val member = MemberUtil.createMember()
        val massageCount = MassageUtil.createMassageCount()
        val massage = MassageUtil.createMassage()

        init(userUtil, member, validDayOfWeekAndHourMassageUtil, findMassageCountUtil, massageCount, massageCheckUtil, saveMassageUtil, massage)
        `when`("서비스를 실행할때") {
            applyMassageServiceImpl.execute()
            then("사용자의 massageStatus는 APPLIED로 변경되어야함") {
                member.massageStatus shouldBe MassageStatus.APPLIED
            }
            then("MassageCount는 1이 증가해야함") {
                massageCount.count shouldBe 1
            }
            then("MassageSaveUtil이 실행되어야함") {
                verify(exactly = 1) { saveMassageUtil.save(member) }
            }
        }

        every { validDayOfWeekAndHourMassageUtil.validateApply() } throws NotMassageApplyDayException()
        `when`("신청할 수 있는 요일이 아닐때") {
            then("NotMassageApplyDayException이 발생해야함") {
                shouldThrow<NotMassageApplyDayException> {
                    applyMassageServiceImpl.execute()
                }
            }
        }
        init(userUtil, member, validDayOfWeekAndHourMassageUtil, findMassageCountUtil, massageCount, massageCheckUtil, saveMassageUtil, massage)

        every { validDayOfWeekAndHourMassageUtil.validateApply() } throws NotMassageApplyHourException()
        `when`("신청할 수 있는 시간이 아닐때") {
            then("NotMassageApplyHourException이 발생해야함") {
                shouldThrow<NotMassageApplyHourException> {
                    applyMassageServiceImpl.execute()
                }
            }
        }
        init(userUtil, member, validDayOfWeekAndHourMassageUtil, findMassageCountUtil, massageCount, massageCheckUtil, saveMassageUtil, massage)

        every { findMassageCountUtil.findMassageCount() } returns MassageUtil.createMassageCount(count = 5)
        `when`("신청인원이 가득 차 있을때") {
            then("MassageOverException이 발생해야함") {
                shouldThrow<MassageOverException> {
                    applyMassageServiceImpl.execute()
                }
            }
        }
        init(userUtil, member, validDayOfWeekAndHourMassageUtil, findMassageCountUtil, massageCount, massageCheckUtil, saveMassageUtil, massage)

        every { massageCheckUtil.isMassageStatusCan(member) } throws AlreadyApplySelfStudyException()
        `when`("유저가 안마의자 신청할 수 없을때") {
            then("AlreadyApplySelfStudyException이 발생해야함") {
                shouldThrow<AlreadyApplySelfStudyException> {
                    applyMassageServiceImpl.execute()
                }
            }
        }
    }
})

private fun init(
    userUtil: UserUtil,
    member: Member,
    validDayOfWeekAndHourMassageUtil: ValidDayOfWeekAndHourMassageUtil,
    findMassageCountUtil: FindMassageCountUtil,
    massageCount: MassageCount,
    massageCheckUtil: MassageCheckUtil,
    saveMassageUtil: SaveMassageUtil,
    massage: Massage,
) {
    every { userUtil.fetchCurrentUser() } returns member
    every { validDayOfWeekAndHourMassageUtil.validateApply() } returns Unit
    every { findMassageCountUtil.findMassageCount() } returns massageCount
    every { massageCheckUtil.isMassageStatusCan(member) } returns Unit
    every { saveMassageUtil.save(member) } returns massage
}