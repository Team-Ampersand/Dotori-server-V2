package com.dotori.v2.domain.main_page.service

import com.dotori.v2.domain.main_page.presentation.dto.res.BoardAlarmResDto

interface BoardAlarmService {
    fun execute(): BoardAlarmResDto
}