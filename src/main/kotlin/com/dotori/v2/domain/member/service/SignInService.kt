package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.member.presentation.dto.req.SignInReqDto
import com.dotori.v2.domain.member.presentation.dto.res.SignInResDto

interface SignInService {
    fun execute(signInReqDto: SignInReqDto): SignInResDto
}