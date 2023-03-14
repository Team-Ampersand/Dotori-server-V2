package com.dotori.v2.domain.rule.service.impl

import com.dotori.v2.domain.rule.domain.entity.RuleViolation
import com.dotori.v2.domain.rule.presentation.data.dto.RuleDto
import com.dotori.v2.domain.rule.presentation.data.res.RuleListResDto
import com.dotori.v2.domain.rule.service.FindCouncillorRuleService
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class FindCouncillorServiceImpl(
    private val userUtil: UserUtil
) : FindCouncillorRuleService {
    override fun execute(): RuleListResDto {
        val member = userUtil.fetchCurrentUser()
        return RuleListResDto(
            rules = member.ruleViolation
                .map { it.toDto() }
        )
    }

    private fun RuleViolation.toDto(): RuleDto =
        RuleDto(
            rule = this.rule
        )
}