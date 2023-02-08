package com.dotori.v2.domain.member.service.impl

import com.dotori.v2.domain.member.presentation.dto.res.LogoutResDto
import com.dotori.v2.domain.member.service.LogoutService
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class LogoutServiceImpl(
    private val userUtil: UserUtil,
) : LogoutService{
    override fun execute(): LogoutResDto =
        userUtil.fetchCurrentUser()
            .let { it.updateRefreshToken("") }
            .let { LogoutResDto(it) }

}