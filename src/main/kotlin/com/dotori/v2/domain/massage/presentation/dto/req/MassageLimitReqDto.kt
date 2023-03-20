package com.dotori.v2.domain.massage.presentation.dto.req

import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class MassageLimitReqDto(
    @field:Max(value = 5, message = "안마의자 제한 인원은 5명을 넘을 수 없습니다.")
    @field:Min(value = 0, message = "안마의자 인원은 최소 0명이여야 합니다.")
    val limit: Int
)
