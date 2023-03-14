package com.dotori.v2.domain.member.presentation.data.dto

import com.dotori.v2.domain.rule.enums.Rule

data class MemberDto(
    val id: Long,
    val memberName: String,
    val stuNum: String,
    val selfStudyStatus: Boolean,
    val rule: List<Rule>
)
