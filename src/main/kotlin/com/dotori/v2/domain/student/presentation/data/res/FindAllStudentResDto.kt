package com.dotori.v2.domain.student.presentation.data.res

import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.enums.SelfStudyStatus

data class FindAllStudentResDto(
    val id: Long,
    val gender: Gender,
    val memberName: String,
    val stuNum: String,
    val role: Role,
    val selfStudyStatus: SelfStudyStatus,
    val profileImage: String?
)