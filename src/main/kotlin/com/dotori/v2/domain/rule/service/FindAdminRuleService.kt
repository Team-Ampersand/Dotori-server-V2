package com.dotori.v2.domain.rule.service

import com.dotori.v2.domain.rule.presentation.data.res.RuleListResDto

interface FindAdminRuleService {
    fun execute(stuNum: String): RuleListResDto
}