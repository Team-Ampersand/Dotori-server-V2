package com.dotori.v2.domain.self_study.service

import com.dotori.v2.domain.self_study.presentation.dto.req.SelfStudyCheckReqDto
import java.util.*

interface UpdateSelfStudyCheckService {
    fun execute(memberId: UUID, selfStudyCheckReqDto: SelfStudyCheckReqDto)
}