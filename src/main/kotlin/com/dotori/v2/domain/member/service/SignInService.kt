package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.member.presentation.data.dto.SignInDto
import com.dotori.v2.domain.member.presentation.data.res.SignInResDto

interface SignInService {
    fun execute(signInDto: SignInDto): SignInResDto
}