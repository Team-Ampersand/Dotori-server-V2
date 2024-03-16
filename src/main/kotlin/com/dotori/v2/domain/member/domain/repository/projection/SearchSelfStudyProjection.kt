package com.dotori.v2.domain.member.domain.repository.projection

import com.dotori.v2.domain.member.enums.Gender
import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.querydsl.core.annotations.QueryProjection

data class SearchSelfStudyProjection @QueryProjection constructor(
    val id: Long,
    val name: String,
    val stuNum: String,
    val gender: Gender,
    val selfStudyStatus: SelfStudyStatus,
    val profileImage: String?
)
