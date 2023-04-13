package com.dotori.v2.domain.self_study.service

import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.stu_info.exception.SelfStudySearchReqDto
import com.dotori.v2.domain.stu_info.presentation.data.req.SearchRequestDto

interface GetSelfStudyByStuNumAndNameService {
    fun execute(searchRequestDto: SelfStudySearchReqDto): SelfStudyMemberListResDto
}