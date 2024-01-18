package com.dotori.v2.domain.selfstudy.service

import com.dotori.v2.domain.selfstudy.presentation.dto.res.SelfStudyInfoResDto

interface GetSelfStudyInfoService {
    fun execute(): SelfStudyInfoResDto
}