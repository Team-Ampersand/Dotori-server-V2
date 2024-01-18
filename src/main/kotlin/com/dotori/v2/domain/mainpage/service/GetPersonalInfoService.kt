package com.dotori.v2.domain.mainpage.service

import com.dotori.v2.domain.mainpage.presentation.dto.res.PersonalInfoResDto

interface GetPersonalInfoService {
    fun execute(): PersonalInfoResDto
}