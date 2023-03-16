package com.dotori.v2.domain.self_study.service

import com.dotori.v2.domain.self_study.presentation.dto.req.SelfStudyLimitReqDto

interface ChangeSelfStudyLimitService {
    fun execute(selfStudyLimitReqDto: SelfStudyLimitReqDto)
}