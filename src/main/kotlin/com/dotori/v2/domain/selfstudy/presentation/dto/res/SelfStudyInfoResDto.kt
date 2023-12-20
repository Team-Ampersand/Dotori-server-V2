package com.dotori.v2.domain.selfstudy.presentation.dto.res

import com.dotori.v2.domain.member.enums.SelfStudyStatus

data class SelfStudyInfoResDto (
    val count: Long,
    val limit: Int,
    val selfStudyStatus: SelfStudyStatus
)