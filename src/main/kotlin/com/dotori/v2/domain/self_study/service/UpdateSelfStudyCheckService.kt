package com.dotori.v2.domain.self_study.service

import com.dotori.v2.domain.self_study.presentation.dto.req.SelfStudyCheckReqDto

interface UpdateSelfStudyCheckService {
    fun execute(memberId: Long, selfStudyCheckReqDto: SelfStudyCheckReqDto)
}