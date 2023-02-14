package com.dotori.v2.domain.member.service

import com.dotori.v2.domain.member.presentation.dto.res.RefreshResDto

interface RefreshService {
    fun execute(refreshToken: String): RefreshResDto
}