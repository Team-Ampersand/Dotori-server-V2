package com.dotori.v2.domain.self_study.presentation.dto.res

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Gender

data class SelfStudyMemberResDto(
    val id: Long,
    val stuNum: String,
    val memberName: String,
    val gender: Gender,
    val selfStudyCheck: Boolean
) {
    constructor(member: Member) : this(id = member.id, stuNum = member.stuNum, memberName = member.memberName, gender = member.gender, selfStudyCheck = member.selfStudyCheck)
}