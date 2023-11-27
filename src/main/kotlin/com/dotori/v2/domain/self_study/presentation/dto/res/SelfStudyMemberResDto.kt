package com.dotori.v2.domain.self_study.presentation.dto.res

import com.dotori.v2.domain.member.domain.entity.Member

data class SelfStudyMemberResDto(
    val rank: Long,
    val id: Long,
    val stuNum: String,
    val memberName: String,
    val gender: String,
    val selfStudyCheck: Boolean,
    val profileUrl: String?
) {
    constructor(rank: Long, member: Member) : this(rank = rank, id = member.id, stuNum = member.stuNum, memberName = member.memberName, gender = member.gender, selfStudyCheck = member.selfStudyCheck, profileUrl = member.profileImage)
}