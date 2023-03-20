package com.dotori.v2.domain.self_study.presentation.dto.req

import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class SelfStudyLimitReqDto(
    @field:Max(value = 50, message = "자습 인원은 50명을 넘어갈 수 없습니다")
    @field:Min(value = 0, message = "자습인원은 최소 0명이여야 합니다.")
    val limit: Int
)