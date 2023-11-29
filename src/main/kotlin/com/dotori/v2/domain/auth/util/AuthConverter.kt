package com.dotori.v2.domain.auth.util

import com.dotori.v2.domain.auth.domain.entity.RefreshToken
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.presentation.data.dto.SignInDto
import com.dotori.v2.domain.member.presentation.data.req.SignInReqDto
import gauth.GAuthUserInfo

interface AuthConverter {

    fun toDto(signInReqDto: SignInReqDto): SignInDto

    fun toEntity(gAuthUserInfo: GAuthUserInfo): Member

    fun toAdminEntity(gAuthUserInfo: GAuthUserInfo): Member

    fun toCouncillorEntity(gAuthUserInfo: GAuthUserInfo): Member

    fun toDeveloperEntity(gAuthUserInfo: GAuthUserInfo): Member

    fun toEntity(memberInfo: Member, refreshToken: String): RefreshToken

    fun toEntity(memberId: Long?, refreshToken: String): RefreshToken

}