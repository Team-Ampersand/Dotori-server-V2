package com.dotori.v2.domain.stu_info.presentation.data.req

import com.dotori.v2.domain.member.enums.Role
import java.util.*

data class ModifyStudentInfoRequest(
    val memberId: UUID,
    val memberName: String,
    val stuNum: String,
    val gender: String,
    val role: Role
)