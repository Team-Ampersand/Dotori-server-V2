package com.dotori.v2.domain.stu_info.service

import com.dotori.v2.domain.stu_info.presentation.data.res.FindAllStudentResDto

interface FindAllMemberService {
    fun execute(): List<FindAllStudentResDto>
}