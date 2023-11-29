package com.dotori.v2.domain.self_study.util

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.self_study.exception.AlreadyApplySelfStudyException
import com.dotori.v2.domain.self_study.exception.NotAppliedException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.util.*

class SelfStudyCheckUtilTest : BehaviorSpec({
    val selfStudyCheckUtil = SelfStudyCheckUtil()
    given("자습 신청가능한 유저가 주어지고") {
        val canMember = Member(
            memberName = "test",
            stuNum = "1111",
            email = "test@gsm.hs.kr",
            ruleViolation = mutableListOf(),
            gender = "MALE",
            roles = Collections.singletonList(Role.ROLE_MEMBER),
            profileImage = null
        )
        `when`("자습 신청이 가능한지 검사하면") {
            val result = selfStudyCheckUtil.isSelfStudyStatusCan(canMember)
            then("아무 예외없이 통과되어야함") {
                result shouldBe Unit
            }
        }
        `when`("자습 신청했는지 검사하면") {
            then("NotAppliedException이 발생해야함") {
                shouldThrow<NotAppliedException> {
                    selfStudyCheckUtil.isSelfStudyStatusApplied(canMember)
                }
            }
        }
    }

    given("자습 신청한 유저가 주어지고") {
        val appliedMember = Member(
            memberName = "test",
            stuNum = "1111",
            email = "test@gsm.hs.kr",
            ruleViolation = mutableListOf(),
            gender = "MALE",
            roles = Collections.singletonList(Role.ROLE_MEMBER),
            profileImage = null
        )
        appliedMember.updateSelfStudyStatus(SelfStudyStatus.APPLIED)
        `when`("자습 신청이 가능한지 검사하면") {
            then("AlreadyApplySelfStudyException이 발생해야함") {
                shouldThrow<AlreadyApplySelfStudyException> {
                    selfStudyCheckUtil.isSelfStudyStatusCan(appliedMember)
                }
            }
        }
        `when`("자습 신청했는지 검사하면") {
            val result = selfStudyCheckUtil.isSelfStudyStatusApplied(appliedMember)
            then("아무 예외없이 통과되어야함") {
                result shouldBe Unit
            }
        }
    }
})