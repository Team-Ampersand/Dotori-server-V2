package com.dotori.v2.domain.auth.util.impl

import com.dotori.v2.domain.auth.domain.entity.RefreshToken
import com.dotori.v2.domain.auth.domain.repository.RefreshTokenRepository
import com.dotori.v2.domain.auth.util.AuthConverter
import com.dotori.v2.domain.auth.util.AuthUtil
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.domain.repository.MemberRepository
import gauth.GAuthUserInfo
import org.springframework.stereotype.Component

@Component
class AuthUtilImpl(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val authConverter: AuthConverter,
    private val memberRepository: MemberRepository
) : AuthUtil {
    override fun saveNewUser(gAuthUserInfo: GAuthUserInfo, refreshToken: String) {
        val signInUserInfo: Member = authConverter.toEntity(gAuthUserInfo)
            .let { memberRepository.save(it) }
        saveNewRefreshToken(signInUserInfo, refreshToken)
    }

    override fun saveNewAdmin(gAuthUserInfo: GAuthUserInfo, refreshToken: String) {
        val signInAdminInfo: Member = authConverter.toAdminEntity(gAuthUserInfo)
            .let { memberRepository.save(it) }
        saveNewRefreshToken(signInAdminInfo, refreshToken)
    }

    override fun saveNewCouncillor(gAuthUserInfo: GAuthUserInfo, refreshToken: String) {
        val signInTeacherInfo: Member = authConverter.toCouncillorEntity(gAuthUserInfo)
            .let { memberRepository.save(it) }
        saveNewRefreshToken(signInTeacherInfo, refreshToken)
    }

    override fun saveNewDeveloper(gAuthUserInfo: GAuthUserInfo, refreshToken: String) {
        val signInTeacherInfo: Member = authConverter.toDeveloperEntity(gAuthUserInfo)
            .let { memberRepository.save(it) }
        saveNewRefreshToken(signInTeacherInfo, refreshToken)
    }

    override fun saveNewRefreshToken(memberInfo: Member, refreshToken: String): RefreshToken {
        return authConverter.toEntity(memberInfo, refreshToken)
            .let { refreshTokenRepository.save(it) }
    }
}