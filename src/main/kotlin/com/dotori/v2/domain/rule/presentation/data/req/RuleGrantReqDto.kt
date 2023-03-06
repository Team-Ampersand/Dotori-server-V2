package com.dotori.v2.domain.rule.presentation.data.req

import com.dotori.v2.domain.rule.enums.Rule
import java.time.LocalDate

data class RuleGrantReqDto(
    val stuNum: List<String>,
    val rule: List<Rule>,
    val date: LocalDate
)
