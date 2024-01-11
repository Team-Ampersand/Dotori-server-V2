package com.dotori.v2.domain.member.service.impl

import com.dotori.v2.domain.member.exception.PasswordMismatchException
import com.dotori.v2.domain.member.presentation.data.req.NewPasswordReqDto
import com.dotori.v2.domain.member.service.ChangeAuthPasswordService
import com.dotori.v2.global.util.UserUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class ChangeAuthPasswordServiceImpl(
    private val userUtil: UserUtil,
    private val passwordEncoder: PasswordEncoder
): ChangeAuthPasswordService {
    override fun execute(newPasswordReqDto: NewPasswordReqDto) {
        val member = userUtil.fetchCurrentUser()

        if (!passwordEncoder.matches(newPasswordReqDto.currentPassword, member.password))
            throw PasswordMismatchException()

        member.updatePassword(passwordEncoder.encode(newPasswordReqDto.newPassword))
    }
}