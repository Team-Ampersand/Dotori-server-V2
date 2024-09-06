package com.dotori.v2.domain.student.service

import com.dotori.v2.domain.student.presentation.data.res.FindAllStudentListResDto

interface FindAllMemberService {
    fun execute(): FindAllStudentListResDto
}