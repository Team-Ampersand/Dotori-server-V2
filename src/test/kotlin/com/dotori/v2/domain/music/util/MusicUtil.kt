package com.dotori.v2.domain.music.util

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.util.MemberUtil
import com.dotori.v2.domain.music.domain.entity.Music

class MusicUtil {
    companion object {
        fun createMusic(
            id: Long = 0,
            url: String = "https://www.test.url",
            member: Member = MemberUtil.createMember()
        ) = Music(id, url, member)
    }

}