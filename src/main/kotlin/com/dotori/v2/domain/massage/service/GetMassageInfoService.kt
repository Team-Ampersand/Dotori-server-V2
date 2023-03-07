package com.dotori.v2.domain.massage.service

import com.dotori.v2.domain.massage.presentation.dto.res.MassageInfoResDto

interface GetMassageInfoService {
    fun execute(): MassageInfoResDto
}