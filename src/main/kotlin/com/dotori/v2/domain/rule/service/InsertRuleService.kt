package com.dotori.v2.domain.rule.service

import com.dotori.v2.domain.rule.presentation.data.req.RuleGrantReqDto

interface InsertRuleService {
    fun execute(ruleGrantReqDto: RuleGrantReqDto)
}