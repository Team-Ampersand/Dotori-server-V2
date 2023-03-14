package com.dotori.v2.domain.rule.service

import com.dotori.v2.domain.rule.presentation.data.res.MemberListResDto

interface FindStudentService {
    fun execute(stuNum: String?, memberName: String?): MemberListResDto
}