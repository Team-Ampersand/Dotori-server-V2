package com.dotori.v2.domain.rule.util

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.util.MemberUtil
import com.dotori.v2.domain.rule.domain.entity.RuleViolation
import com.dotori.v2.domain.rule.enums.Rule
import java.time.LocalDate

class RuleViolationUtil(
    private val memberUtil: MemberUtil
) {
    fun createRuleViolation(
        id: Long = 0,
        date: LocalDate = LocalDate.now(),
        rule: Rule = Rule.EAT_FOOD,
        member: Member = memberUtil.createMember()
    ) = RuleViolation(id, date, rule, member)
}