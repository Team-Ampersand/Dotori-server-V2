package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.member.presentation.dto.res.LogoutResDto

interface LogoutService {
    fun execute(): LogoutResDto
}