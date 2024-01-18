package com.dotori.v2.domain.auth.service

import com.dotori.v2.domain.auth.presentation.data.req.SignUpReqDto

interface SignUpService {
    fun execute(signUpReqDto: SignUpReqDto)
}