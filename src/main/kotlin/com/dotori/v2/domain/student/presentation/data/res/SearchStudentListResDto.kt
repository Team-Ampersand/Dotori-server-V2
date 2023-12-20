package com.dotori.v2.domain.student.presentation.data.res

import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.enums.SelfStudyStatus

data class SearchStudentListResDto(
    val id: Long,
    val email: String?,
    val memberName: String,
    val stuNum: String,
    val gender: String,
    val role: Role,
    val selfStudyStatus: SelfStudyStatus
)