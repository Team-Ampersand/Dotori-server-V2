package com.dotori.v2.domain.massage.util

import com.dotori.v2.domain.massage.domain.entity.Massage
import com.dotori.v2.domain.massage.domain.entity.MassageCount
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.util.MemberUtil

class MassageUtil(
    private val memberUtil: MemberUtil
) {
    fun createMassage(
        id: Long = 0,
        member: Member = memberUtil.createMember()
    ) = Massage(id, member)

    fun createMassageCount(
        id: Long = 1,
        count: Long = 0,
        limit: Int = 5
    ) = MassageCount(id, count, limit)
}