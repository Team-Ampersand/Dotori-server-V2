package com.dotori.v2.domain.student.service

import com.dotori.v2.domain.student.presentation.data.res.FindAllStudentResDto

interface FindAllMemberService {
    fun execute(): List<FindAllStudentResDto>
}