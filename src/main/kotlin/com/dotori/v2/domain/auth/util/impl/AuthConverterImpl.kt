package com.dotori.v2.domain.auth.util.impl

import com.dotori.v2.domain.auth.domain.entity.RefreshToken
import com.dotori.v2.domain.auth.presentation.data.dto.SignInEmailAndPasswordDto
import com.dotori.v2.domain.auth.util.AuthConverter
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.auth.presentation.data.req.SignInEmailAndPasswordReqDto
import com.dotori.v2.domain.member.enums.Gender
import org.springframework.stereotype.Component

@Component
class AuthConverterImpl : AuthConverter {

    override fun toDto(signInEmailAndPasswordReqDto: SignInEmailAndPasswordReqDto): SignInEmailAndPasswordDto =
        SignInEmailAndPasswordDto(
            email = signInEmailAndPasswordReqDto.email,
            password = signInEmailAndPasswordReqDto.password
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