package com.dotori.v2.domain.main_page.service

import com.dotori.v2.domain.main_page.presentation.dto.res.PersonalInfoResDto

interface GetPersonalInfoService {
    fun execute(): PersonalInfoResDto
}