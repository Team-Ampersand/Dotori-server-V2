package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.member.presentation.data.req.SignInReqDto
import com.dotori.v2.domain.member.presentation.data.res.SignInResDto

interface SignInService {
    fun execute(signInReqDto: SignInReqDto): SignInResDto
}