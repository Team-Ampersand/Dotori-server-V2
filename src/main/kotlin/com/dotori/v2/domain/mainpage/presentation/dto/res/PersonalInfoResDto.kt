package com.dotori.v2.domain.mainpage.presentation.dto.res

import com.dotori.v2.domain.member.domain.entity.Member

data class PersonalInfoResDto(
    val id: Long,
    val stuNum: String,
    val name: String,
    val gender: String,
    val profileImage: String?
) {
    constructor(member: Member) : this(id = member.id, stuNum = member.stuNum, name = member.memberName, gender = member.gender, profileImage = member.profileImage)
}