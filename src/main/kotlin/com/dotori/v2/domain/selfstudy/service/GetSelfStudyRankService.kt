package com.dotori.v2.domain.selfstudy.service

import com.dotori.v2.domain.selfstudy.presentation.dto.res.SelfStudyMemberListResDto

interface GetSelfStudyRankService {
    fun execute(): SelfStudyMemberListResDto
}