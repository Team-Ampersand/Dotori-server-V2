package com.dotori.v2.domain.selfstudy.service

import com.dotori.v2.domain.selfstudy.presentation.dto.res.SelfStudyMemberListResDto

interface GetSelfStudyByStuNumService {
    fun execute(stuNum: String): SelfStudyMemberListResDto
}