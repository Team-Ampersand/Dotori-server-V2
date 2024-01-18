package com.dotori.v2.domain.selfstudy.util

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.util.MemberUtil
import com.dotori.v2.domain.selfstudy.domain.entity.SelfStudy
import com.dotori.v2.domain.selfstudy.domain.entity.SelfStudyCount

class SelfStudyUtil {
    companion object {
        fun createSelfStudy(
            id: Long = 0,
            member: Member = MemberUtil.createMember()
        ) = SelfStudy(id, member)

        fun createSelfStudyCount(
            id: Long = 1,
            count: Long = 0,
            limit: Int = 50
        ) = SelfStudyCount(id, count, limit)
    }

}