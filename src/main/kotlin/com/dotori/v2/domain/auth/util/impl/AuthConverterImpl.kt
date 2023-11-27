package com.dotori.v2.domain.auth.util.impl

import com.dotori.v2.domain.auth.domain.entity.RefreshToken
import com.dotori.v2.domain.auth.util.AuthConverter
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.member.presentation.data.dto.SignInDto
import com.dotori.v2.domain.member.presentation.data.req.SignInReqDto
import gauth.GAuthUserInfo
import org.springframework.stereotype.Component

@Component
class AuthConverterImpl : AuthConverter {
    override fun toDto(signInReqDto: SignInReqDto): SignInDto =
        SignInDto(
                code = signInReqDto.code
        )

    override fun toEntity(gAuthUserInfo: GAuthUserInfo): Member =
        Member(
            memberName = gAuthUserInfo.name,
            stuNum = "${gAuthUserInfo.grade}${gAuthUserInfo.classNum}${gAuthUserInfo.num}",
            email = gAuthUserInfo.email,
            gender = gAuthUserInfo.gender,
            roles = mutableListOf(Role.ROLE_MEMBER),
            ruleViolation = mutableListOf(),
            profileImage = gAuthUserInfo.profileUrl
        )

    override fun toAdminEntity(gAuthUserInfo: GAuthUserInfo): Member =
        Member(
            memberName = gAuthUserInfo.name,
            stuNum = "${gAuthUserInfo.grade}${gAuthUserInfo.classNum}${gAuthUserInfo.num}",
            email = gAuthUserInfo.email,
            gender = gAuthUserInfo.gender,
            roles = mutableListOf(Role.ROLE_ADMIN),
            ruleViolation = mutableListOf(),
            profileImage = gAuthUserInfo.profileUrl
        )

    override fun toCouncillorEntity(gAuthUserInfo: GAuthUserInfo): Member =
        Member(
            memberName = gAuthUserInfo.name,
            stuNum = "${gAuthUserInfo.grade}${gAuthUserInfo.classNum}${gAuthUserInfo.num}",
            email = gAuthUserInfo.email,
            gender = gAuthUserInfo.gender,
            roles = mutableListOf(Role.ROLE_COUNCILLOR),
            ruleViolation = mutableListOf(),
            profileImage = gAuthUserInfo.profileUrl
        )

    override fun toDeveloperEntity(gAuthUserInfo: GAuthUserInfo): Member =
        Member(
            memberName = gAuthUserInfo.name,
            stuNum = "${gAuthUserInfo.grade}${gAuthUserInfo.classNum}${gAuthUserInfo.num}",
            email = gAuthUserInfo.email,
            gender = gAuthUserInfo.gender,
            roles = mutableListOf(Role.ROLE_DEVELOPER),
            ruleViolation = mutableListOf(),
            profileImage = gAuthUserInfo.profileUrl
        )

    override fun toEntity(memberInfo: Member, refreshToken: String): RefreshToken =
        RefreshToken(
            memberId = memberInfo.id,
            token = refreshToken
        )


    override fun toEntity(memberId: Long?, refreshToken: String): RefreshToken =
        RefreshToken(
            memberId = memberId,
            token = refreshToken
        )
}