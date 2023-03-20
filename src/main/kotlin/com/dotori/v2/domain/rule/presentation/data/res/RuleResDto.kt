package com.dotori.v2.domain.rule.presentation.data.res

import com.dotori.v2.domain.rule.enums.Rule
import java.time.LocalDate

data class RuleResDto(
    val id: Long,
    val rule: Rule,
    val createdDate: LocalDate
)
