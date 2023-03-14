package com.dotori.v2.domain.self_study.presentation.dto.res

import com.dotori.v2.domain.member.enums.SelfStudyStatus

data class SelfStudyInfoResDto (
    val count: Long,
    val selfStudyStatus: SelfStudyStatus
)