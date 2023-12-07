package com.dotori.v2.domain.auth.service

import com.dotori.v2.domain.auth.presentation.data.dto.SignInGAuthDto
import com.dotori.v2.domain.auth.presentation.data.res.SignInResDto

interface SignInGAuthService {
    fun execute(signInDto: SignInGAuthDto): SignInResDto
}