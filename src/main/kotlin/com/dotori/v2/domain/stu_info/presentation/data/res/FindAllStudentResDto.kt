package com.dotori.v2.domain.stu_info.presentation.data.res

import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.enums.SelfStudyStatus
import java.util.*

data class FindAllStudentResDto(
    val id: UUID,
    val gender: String,
    val memberName: String,
    val stuNum: String,
    val role: Role,
    val selfStudyStatus: SelfStudyStatus
)