package com.dotori.v2.domain.student.presentation.data.req

import com.dotori.v2.domain.member.enums.Role

data class ModifyStudentInfoRequest(
    val memberId: Long,
    val memberName: String,
    val stuNum: String,
    val gender: String,
    val role: Role
)