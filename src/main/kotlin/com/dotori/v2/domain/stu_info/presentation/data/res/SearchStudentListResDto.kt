package com.dotori.v2.domain.stu_info.presentation.data.res

import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.enums.SelfStudyStatus

data class SearchStudentListResDto(
    val id: Long,
    val email: String?,
    val memberName: String,
    val stuNum: String,
    val gender: Gender,
    val role: Role,
    val selfStudyStatus: SelfStudyStatus
)