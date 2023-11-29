package com.dotori.v2.domain.auth.service

import com.dotori.v2.domain.auth.presentation.data.res.RefreshResDto

interface RefreshTokenService {
    fun execute(refreshToken: String): RefreshResDto
}