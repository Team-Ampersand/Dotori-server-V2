package com.dotori.v2.domain.rule.presentation.data.dto

import com.dotori.v2.domain.rule.enums.Rule
import java.time.LocalDate

data class RuleDto(
    val id: Long,
    val rule: Rule,
    val createdDate: LocalDate
)
