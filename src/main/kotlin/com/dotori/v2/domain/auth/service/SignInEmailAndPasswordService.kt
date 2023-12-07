package com.dotori.v2.domain.auth.service

import com.dotori.v2.domain.auth.presentation.data.dto.SignInEmailAndPasswordDto
import com.dotori.v2.domain.auth.presentation.data.res.SignInResDto

@Deprecated("GAuth 로그인으로 변환")
interface SignInEmailAndPasswordService {
    fun execute(signInEmailAndPasswordDto: SignInEmailAndPasswordDto): SignInResDto
}