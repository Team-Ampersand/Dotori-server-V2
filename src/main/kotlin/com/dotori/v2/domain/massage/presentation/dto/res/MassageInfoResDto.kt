package com.dotori.v2.domain.massage.presentation.dto.res

import com.dotori.v2.domain.member.enums.MassageStatus

data class MassageInfoResDto(
    val count: Long,
    val massageStatus: MassageStatus,
    val limit: Int
)