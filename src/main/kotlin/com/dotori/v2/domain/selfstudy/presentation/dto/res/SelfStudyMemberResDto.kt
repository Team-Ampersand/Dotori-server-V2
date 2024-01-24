package com.dotori.v2.domain.selfstudy.presentation.dto.res

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Gender

data class SelfStudyMemberResDto(
    val rank: Long,
    val id: Long,
    val stuNum: String,
    val memberName: String,
    val gender: Gender,
    val selfStudyCheck: Boolean,
    val profileImage: String?
) {
    constructor(rank: Long, member: Member) : this(rank = rank, id = member.id, stuNum = member.stuNum, memberName = member.memberName, gender = member.gender, selfStudyCheck = member.selfStudyCheck, profileImage = member.profileImage)
}