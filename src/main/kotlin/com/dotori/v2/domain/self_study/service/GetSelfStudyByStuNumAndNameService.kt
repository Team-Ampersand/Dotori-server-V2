package com.dotori.v2.domain.self_study.service

import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.self_study.presentation.dto.req.SelfStudySearchReqDto

interface GetSelfStudyByStuNumAndNameService {
    fun execute(searchRequestDto: SelfStudySearchReqDto): SelfStudyMemberListResDto
}