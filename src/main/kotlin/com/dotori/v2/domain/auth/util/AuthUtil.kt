package com.dotori.v2.domain.auth.util

import com.dotori.v2.domain.auth.domain.entity.RefreshToken
import com.dotori.v2.domain.member.domain.entity.Member
import gauth.GAuthUserInfo

interface AuthUtil {
    fun saveNewUser(gAuthUserInfo: GAuthUserInfo, refreshToken: String)

    fun saveNewAdmin(gAuthUserInfo: GAuthUserInfo, refreshToken: String)

    fun saveNewCouncillor(gAuthUserInfo: GAuthUserInfo, refreshToken: String)

    fun saveNewDeveloper(gAuthUserInfo: GAuthUserInfo, refreshToken: String)

    fun saveNewRefreshToken(memberInfo: Member, refreshToken: String): RefreshToken
}