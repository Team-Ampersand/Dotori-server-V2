package com.dotori.v2.domain.member.presentation.data.dto

import com.dotori.v2.domain.rule.enums.Rule
import java.util.*

data class MemberDto(
    val id: UUID,
    val memberName: String,
    val stuNum: String,
    val gender: String,
    val rule: List<Rule>
)