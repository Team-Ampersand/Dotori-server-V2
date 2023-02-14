package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.member.presentation.dto.req.WithdrawalReqDto

interface WithdrawalService {
    fun execute(withdrawalReqDto: WithdrawalReqDto)
}