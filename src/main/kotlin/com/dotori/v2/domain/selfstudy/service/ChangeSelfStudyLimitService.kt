package com.dotori.v2.domain.selfstudy.service

import com.dotori.v2.domain.selfstudy.presentation.dto.req.SelfStudyLimitReqDto

interface ChangeSelfStudyLimitService {
    fun execute(selfStudyLimitReqDto: SelfStudyLimitReqDto)
}