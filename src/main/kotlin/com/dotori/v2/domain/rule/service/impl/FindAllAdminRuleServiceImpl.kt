package com.dotori.v2.domain.rule.service.impl

import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import com.dotori.v2.domain.rule.domain.entity.RuleViolation
import com.dotori.v2.domain.rule.domain.repository.RuleRepository
import com.dotori.v2.domain.rule.presentation.data.res.RuleResDto
import com.dotori.v2.domain.rule.presentation.data.res.RuleListResDto
import com.dotori.v2.domain.rule.service.FindAllAdminRuleService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class FindAllAdminRuleServiceImpl(
    private val ruleRepository: RuleRepository,
    private val memberRepository: MemberRepository
) : FindAllAdminRuleService {
    override fun execute(stuNum: String): RuleListResDto =
        RuleListResDto(
            rules = ruleRepository.findByMember(
                member = memberRepository.findByStuNum(stuNum) ?: throw MemberNotFoundException()
            )
                .map { it.toDto() }
        )

    private fun RuleViolation.toDto(): RuleResDto =
        RuleResDto(
            id = this.id,
            rule = this.rule,
            createdDate = this.date
        )
}