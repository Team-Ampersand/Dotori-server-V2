package com.dotori.v2.domain.member.presentation.data.dto

import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.rule.enums.Rule

data class MemberDto(
    val id: Long,
    val memberName: String,
    val stuNum: String,
    val gender: Gender,
    val rule: List<Rule>
)
