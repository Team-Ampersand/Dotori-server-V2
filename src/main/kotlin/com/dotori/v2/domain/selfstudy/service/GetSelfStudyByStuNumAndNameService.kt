package com.dotori.v2.domain.selfstudy.service

import com.dotori.v2.domain.selfstudy.presentation.dto.res.SelfStudyMemberListResDto
import com.dotori.v2.domain.selfstudy.presentation.dto.req.SelfStudySearchReqDto

interface GetSelfStudyByStuNumAndNameService {
    fun execute(searchRequestDto: SelfStudySearchReqDto): SelfStudyMemberListResDto
}