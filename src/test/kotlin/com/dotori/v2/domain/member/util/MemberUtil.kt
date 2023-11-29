package com.dotori.v2.domain.member.util

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.rule.domain.entity.RuleViolation

class MemberUtil {
    companion object {
        fun createMember(
            id: Long = 0,
            memberName: String = "테스트 이름",
            stuNum: String = "0000",
            email: String = "s00000@gsm.hs.kr",
            gender: String = "MALE",
            roles: MutableList<Role> = mutableListOf(Role.ROLE_MEMBER),
            ruleViolation: MutableList<RuleViolation> = mutableListOf(),
            profileImage: String? = null
        ) = Member(id, memberName, stuNum, email, gender, roles, ruleViolation, profileImage)
    }

}
