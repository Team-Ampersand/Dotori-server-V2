package com.dotori.v2.domain.main_page.service.impl

import com.dotori.v2.domain.main_page.presentation.dto.res.PersonalInfoResDto
import com.dotori.v2.domain.main_page.service.GetPersonalInfoService
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class], readOnly = true)
class GetPersonalInfoServiceImpl(
    private val userUtil: UserUtil
) : GetPersonalInfoService {
    override fun execute(): PersonalInfoResDto =
        PersonalInfoResDto(userUtil.fetchCurrentUser())
}