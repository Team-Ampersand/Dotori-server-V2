package com.dotori.v2.domain.rule.domain.repository

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.rule.domain.entity.RuleViolation
import org.springframework.data.jpa.repository.JpaRepository

interface RuleRepository : JpaRepository<RuleViolation, Long> {
    fun findByMember(member: Member): List<RuleViolation>
}