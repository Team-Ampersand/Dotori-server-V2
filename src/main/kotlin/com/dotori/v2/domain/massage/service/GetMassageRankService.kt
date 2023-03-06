package com.dotori.v2.domain.massage.service

import com.dotori.v2.domain.massage.presentation.dto.res.MassageMemberListResDto

interface GetMassageRankService {
    fun execute(): MassageMemberListResDto
}