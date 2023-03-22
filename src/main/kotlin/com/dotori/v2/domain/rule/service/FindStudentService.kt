package com.dotori.v2.domain.rule.service

import com.dotori.v2.domain.rule.presentation.data.req.StudentListReqDto
import com.dotori.v2.domain.rule.presentation.data.res.MemberListResDto

interface FindStudentService {
    fun execute(studentListReqDto: StudentListReqDto): MemberListResDto
}