package com.dotori.v2.domain.auth.util

import com.dotori.v2.domain.auth.domain.entity.RefreshToken
import com.dotori.v2.domain.auth.presentation.data.dto.SignInEmailAndPasswordDto
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.auth.presentation.data.dto.SignInGAuthDto
import com.dotori.v2.domain.auth.presentation.data.req.SignInEmailAndPasswordReqDto
import com.dotori.v2.domain.auth.presentation.data.req.SignInGAuthReqDto
import com.dotori.v2.domain.member.enums.Role
import gauth.GAuthUserInfo

interface AuthConverter {

    fun toDto(signInGAuthReqDto: SignInGAuthReqDto): SignInGAuthDto

    fun toDto(signInEmailAndPasswordReqDto: SignInEmailAndPasswordReqDto): SignInEmailAndPasswordDto

    fun toEntity(gAuthUserInfo: GAuthUserInfo, role: Role): Member

    fun toAdminEntity(gAuthUserInfo: GAuthUserInfo): Member

    fun toCouncillorEntity(gAuthUserInfo: GAuthUserInfo): Member

    fun toDeveloperEntity(gAuthUserInfo: GAuthUserInfo): Member

    fun toEntity(memberInfo: Member, refreshToken: String): RefreshToken

    fun toEntity(memberId: Long?, refreshToken: String): RefreshToken
}