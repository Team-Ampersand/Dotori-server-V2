package com.dotori.v2.domain.massage.presentation.dto.res

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Gender

data class MassageMemberResDto(
    val rank: Long,
    val id: Long,
    val stuNum: String,
    val memberName: String,
    val gender: Gender,
    val selfStudyCheck: Boolean
) {
    constructor(rank: Long, member: Member) : this(rank = rank, id = member.id, stuNum = member.stuNum, memberName = member.memberName, gender = member.gender, selfStudyCheck = member.selfStudyCheck)
}