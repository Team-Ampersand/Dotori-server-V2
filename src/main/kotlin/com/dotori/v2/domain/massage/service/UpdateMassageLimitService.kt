package com.dotori.v2.domain.massage.service

import com.dotori.v2.domain.massage.presentation.dto.req.MassageLimitReqDto

interface UpdateMassageLimitService {
    fun execute(massageLimitReqDto: MassageLimitReqDto)
}