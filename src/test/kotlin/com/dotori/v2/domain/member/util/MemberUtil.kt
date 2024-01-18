package com.dotori.v2.domain.member.util

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.rule.domain.entity.RuleViolation

class MemberUtil {
    companion object {
        fun createMember(
            id: Long = 0,
            memberName: String = "테스트 이름",
            stuNum: String = "0000",
            password: String = "string1!",
            email: String = "s00000@gsm.hs.kr",
            gender: Gender = Gender.MAN,
            roles: MutableList<Role> = mutableListOf(Role.ROLE_MEMBER),
            ruleViolation: MutableList<RuleViolation> = mutableListOf(),
            profileImage: String? = null
        ) = Member(id, memberName, password , stuNum,email, gender, roles, ruleViolation, profileImage)
    }

}
