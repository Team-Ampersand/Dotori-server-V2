package com.dotori.v2.domain.selfstudy.service

import com.dotori.v2.domain.selfstudy.presentation.dto.req.SelfStudyCheckReqDto

interface UpdateSelfStudyCheckService {
    fun execute(memberId: Long, selfStudyCheckReqDto: SelfStudyCheckReqDto)
}