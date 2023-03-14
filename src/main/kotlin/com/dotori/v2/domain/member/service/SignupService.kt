package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.member.presentation.data.req.SignupReqDto

interface SignupService {
    fun execute(signupReqDto: SignupReqDto): Long
}