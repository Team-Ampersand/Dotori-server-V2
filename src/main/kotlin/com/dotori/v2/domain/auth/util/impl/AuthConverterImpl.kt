package com.dotori.v2.domain.auth.util.impl

import com.dotori.v2.domain.auth.domain.entity.RefreshToken
import com.dotori.v2.domain.auth.presentation.data.dto.SignInEmailAndPasswordDto
import com.dotori.v2.domain.auth.util.AuthConverter
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.Role
import com.dotori.v2.domain.auth.presentation.data.dto.SignInGAuthDto
import com.dotori.v2.domain.auth.presentation.data.req.SignInEmailAndPasswordReqDto
import com.dotori.v2.domain.auth.presentation.data.req.SignInGAuthReqDto
import com.dotori.v2.domain.member.enums.Gender
import gauth.GAuthUserInfo
import org.springframework.stereotype.Component

@Component
class AuthConverterImpl : AuthConverter {
    override fun toDto(signInReqDto: SignInGAuthReqDto): SignInGAuthDto =
        SignInGAuthDto(
                code = signInReqDto.code
        )

    override fun toDto(signInEmailAndPasswordReqDto: SignInEmailAndPasswordReqDto): SignInEmailAndPasswordDto =
        SignInEmailAndPasswordDto(
            email = signInEmailAndPasswordReqDto.email,
            password = signInEmailAndPasswordReqDto.password
        )

    override fun toEntity(gAuthUserInfo: GAuthUserInfo, role: Role): Member =
        Member(
            memberName = gAuthUserInfo.name,
            stuNum = "${gAuthUserInfo.grade}${gAuthUserInfo.classNum}${gAuthUserInfo.num}",
            email = gAuthUserInfo.email,
            password = "",
            gender = convertGender(gAuthUserInfo.gender),
            roles = mutableListOf(role),
            ruleViolation = mutableListOf(),
            profileImage = gAuthUserInfo.profileUrl
        )

    override fun toAdminEntity(gAuthUserInfo: GAuthUserInfo): Member =
        Member(
            memberName = gAuthUserInfo.name,
            stuNum = "${gAuthUserInfo.grade}${gAuthUserInfo.classNum}${gAuthUserInfo.num}",
            email = gAuthUserInfo.email,
            password = "",
            gender = convertGender(gAuthUserInfo.gender),
            roles = mutableListOf(Role.ROLE_ADMIN),
            ruleViolation = mutableListOf(),
            profileImage = gAuthUserInfo.profileUrl
        )

    override fun toCouncillorEntity(gAuthUserInfo: GAuthUserInfo): Member =
        Member(
            memberName = gAuthUserInfo.name,
            stuNum = "${gAuthUserInfo.grade}${gAuthUserInfo.classNum}${gAuthUserInfo.num}",
            email = gAuthUserInfo.email,
            password = "",
            gender = convertGender(gAuthUserInfo.gender),
            roles = mutableListOf(Role.ROLE_COUNCILLOR),
            ruleViolation = mutableListOf(),
            profileImage = gAuthUserInfo.profileUrl
        )

    override fun toDeveloperEntity(gAuthUserInfo: GAuthUserInfo): Member =
        Member(
            memberName = gAuthUserInfo.name,
            stuNum = "${gAuthUserInfo.grade}${gAuthUserInfo.classNum}${gAuthUserInfo.num}",
            email = gAuthUserInfo.email,
            password = "",
            gender = convertGender(gAuthUserInfo.gender),
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

    private fun convertGender(gender: String): Gender =
        when (gender) {
            "MALE" -> Gender.MAN
            "FEMALE" -> Gender.WOMAN
            else -> Gender.PENDING
        }

}