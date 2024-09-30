package com.dotori.v2.domain.auth.util

import com.dotori.v2.domain.auth.domain.entity.RefreshToken
import com.dotori.v2.domain.auth.presentation.data.dto.SignInEmailAndPasswordDto
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.auth.presentation.data.req.SignInEmailAndPasswordReqDto

interface AuthConverter {

    fun toDto(signInEmailAndPasswordReqDto: SignInEmailAndPasswordReqDto): SignInEmailAndPasswordDto

    fun toEntity(memberInfo: Member, refreshToken: String): RefreshToken

    fun toEntity(memberId: Long?, refreshToken: String): RefreshToken
}