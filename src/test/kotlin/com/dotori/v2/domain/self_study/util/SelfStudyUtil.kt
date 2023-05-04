package com.dotori.v2.domain.self_study.util

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.util.MemberUtil
import com.dotori.v2.domain.self_study.domain.entity.SelfStudy
import com.dotori.v2.domain.self_study.domain.entity.SelfStudyCount

class SelfStudyUtil(
    private val memberUtil: MemberUtil
) {
    fun createSelfStudy(
        id: Long = 0,
        member: Member = memberUtil.createMember()
    ) = SelfStudy(id, member)

    fun createSelfStudyCount(
        id: Long = 1,
        count: Long = 0,
        limit: Int = 50
    ) = SelfStudyCount(id, count, limit)
}