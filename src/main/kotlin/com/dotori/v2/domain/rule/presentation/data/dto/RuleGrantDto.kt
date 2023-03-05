package com.dotori.v2.domain.rule.presentation.data.dto

import com.dotori.v2.domain.rule.enums.Rule
import java.time.LocalDate

data class RuleGrantDto(
    val stuNum: List<String>,
    val rule: List<Rule>,
    val date: LocalDate
)
