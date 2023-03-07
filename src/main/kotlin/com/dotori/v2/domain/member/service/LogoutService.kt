package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.member.presentation.data.res.LogoutResDto

interface LogoutService {
    fun execute(): LogoutResDto
}