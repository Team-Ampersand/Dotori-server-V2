package com.dotori.v2.domain.self_study.service

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.self_study.domain.entity.SelfStudyCount
import com.dotori.v2.domain.self_study.domain.repository.SelfStudyRepository
import com.dotori.v2.domain.self_study.service.impl.UpdateSelfStudyStatusServiceImpl
import com.dotori.v2.domain.self_study.util.FindSelfStudyCountUtil
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime
import java.util.*

class UpdateSelfStudyStatusServiceTest : BehaviorSpec({
    val selfStudyRepository = mockk<SelfStudyRepository>()
    val findSelfStudyCountUtil  = mockk<FindSelfStudyCountUtil>()
    val memberRepository = mockk<MemberRepository>()
    val updateSelfStudyStatusServiceImpl = UpdateSelfStudyStatusServiceImpl(selfStudyRepository, memberRepository, findSelfStudyCountUtil)
    given("자습 금지된 유저와 자습 신청한 유저가 주이지고") {
        var appliedMember = createAppliedMember()
        var liftedBanMember = createLiftedBanMember()
        val banedMember = createBanedMember()
        val selfStudyCount = SelfStudyCount(count = 2)
        init(selfStudyRepository, memberRepository, appliedMember, liftedBanMember, findSelfStudyCountUtil, selfStudyCount)
        `when`("서비스를 실행할때") {
            updateSelfStudyStatusServiceImpl.execute()
            then("신청한 사람은 신청가능하게 변경되어야함") {
                appliedMember.selfStudyStatus shouldBe SelfStudyStatus.CAN
                appliedMember.selfStudyCheck shouldBe false
            }
            then("금지헤제일이된 유저의 자습금지는 풀려야함") {
                liftedBanMember.selfStudyStatus shouldBe SelfStudyStatus.CAN
            }
            then("금지헤제일이 안된 유저의 자습금지는 안풀려야함") {
                banedMember.selfStudyStatus shouldBe SelfStudyStatus.IMPOSSIBLE
            }
            then("자습신청 수는 0이 되어야함") {
                selfStudyCount.count shouldBe 0
            }
        }
    }
})

private fun init(
    selfStudyRepository: SelfStudyRepository,
    memberRepository: MemberRepository,
    appliedMember: Member,
    banedMember: Member,
    findSelfStudyCountUtil: FindSelfStudyCountUtil,
    selfStudyCount: SelfStudyCount
) {
    every { selfStudyRepository.deleteAll() } returns Unit
    every { memberRepository.findAllBySelfStudyCheck(true) } returns listOf(appliedMember)
    every { memberRepository.findAllBySelfStudyStatus(SelfStudyStatus.IMPOSSIBLE) } returns listOf(banedMember)
    every { memberRepository.findAll() } returns listOf(appliedMember, banedMember)
    every { findSelfStudyCountUtil.findSelfStudyCount() } returns selfStudyCount
    selfStudyCount.updateCount(2)
}

private fun createLiftedBanMember() : Member {
    val member = Member(
        memberName = "test",
        stuNum = "2116",
        email = "test@gsm.hs.kr",
        gender = "MALE",
        roles = Collections.singletonList(Role.ROLE_MEMBER),
        ruleViolation = mutableListOf(),
        profileImage = null
    )
    member.updateSelfStudyStatus(SelfStudyStatus.IMPOSSIBLE)
    member.updateSelfStudyExpiredDate(LocalDateTime.now())
    return member
}

private fun createBanedMember() : Member {
    val member = Member(
        memberName = "test",
        stuNum = "2116",
        email = "test@gsm.hs.kr",
        gender = "MALE",
        roles = Collections.singletonList(Role.ROLE_MEMBER),
        ruleViolation = mutableListOf(),
        profileImage = null
    )
    member.updateSelfStudyStatus(SelfStudyStatus.IMPOSSIBLE)
    member.updateSelfStudyExpiredDate(LocalDateTime.now().plusDays(7))
    return member
}

private fun createAppliedMember() : Member {
    val member = Member(
        memberName = "test",
        stuNum = "2116",
        email = "test@gsm.hs.kr",
        gender = "MALE",
        roles = Collections.singletonList(Role.ROLE_MEMBER),
        ruleViolation = mutableListOf(),
        profileImage = null
    )
    member.updateSelfStudyStatus(SelfStudyStatus.APPLIED)
    member.updateSelfStudyCheck(true)
    return member
}