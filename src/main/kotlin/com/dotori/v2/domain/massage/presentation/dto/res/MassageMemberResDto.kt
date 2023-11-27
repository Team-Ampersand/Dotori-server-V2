package com.dotori.v2.domain.massage.presentation.dto.res

import com.dotori.v2.domain.member.domain.entity.Member

data class MassageMemberResDto(
    val rank: Long,
    val id: Long,
    val stuNum: String,
    val memberName: String,
    val gender: String,
    val profileUrl: String?
) {
    constructor(rank: Long, member: Member) : this(rank = rank, id = member.id, stuNum = member.stuNum, memberName = member.memberName, gender = member.gender, profileUrl = member.profileImage)
}