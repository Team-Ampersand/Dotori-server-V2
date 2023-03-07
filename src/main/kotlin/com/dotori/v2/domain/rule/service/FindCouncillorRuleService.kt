package com.dotori.v2.domain.rule.service

import com.dotori.v2.domain.rule.presentation.data.res.RuleListResDto

interface FindCouncillorRuleService {
    fun execute(): RuleListResDto
}