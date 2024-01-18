package com.dotori.v2.domain.mainpage.service

import com.dotori.v2.domain.mainpage.presentation.dto.res.BoardAlarmResDto

interface BoardAlarmService {
    fun execute(): BoardAlarmResDto
}