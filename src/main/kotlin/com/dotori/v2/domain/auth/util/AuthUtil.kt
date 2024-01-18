package com.dotori.v2.domain.auth.util

import com.dotori.v2.domain.auth.domain.entity.RefreshToken
import com.dotori.v2.domain.member.domain.entity.Member

interface AuthUtil {
    fun saveNewRefreshToken(memberInfo: Member, refreshToken: String): RefreshToken
}