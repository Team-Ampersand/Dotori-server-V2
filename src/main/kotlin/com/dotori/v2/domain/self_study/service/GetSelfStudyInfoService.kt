package com.dotori.v2.domain.self_study.service

import com.dotori.v2.domain.self_study.presentation.dto.SelfStudyInfoResDto

interface GetSelfStudyInfoService {
    fun execute(): SelfStudyInfoResDto
}