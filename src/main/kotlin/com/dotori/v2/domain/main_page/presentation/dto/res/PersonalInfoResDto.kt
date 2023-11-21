package com.dotori.v2.domain.main_page.presentation.dto.res

import com.dotori.v2.domain.member.domain.entity.Member
import java.util.*

data class PersonalInfoResDto(
    val id: UUID,
    val stuNum: String,
    val name: String,
    val gender: String,
    val profileImage: String?
) {
    constructor(member: Member) : this(id = member.id, stuNum = member.stuNum, name = member.memberName, gender = member.gender, profileImage = member.profileImage)
}