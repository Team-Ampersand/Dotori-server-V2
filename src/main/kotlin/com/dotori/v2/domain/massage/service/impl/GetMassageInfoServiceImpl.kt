package com.dotori.v2.domain.massage.service.impl

import com.dotori.v2.domain.massage.domain.repository.MassageCountRepository
import com.dotori.v2.domain.massage.presentation.dto.res.MassageInfoResDto
import com.dotori.v2.domain.massage.service.GetMassageInfoService
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyInfoResDto
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class GetMassageInfoServiceImpl(
    private val massageCountRepository: MassageCountRepository,
    private val userUtil: UserUtil,
) : GetMassageInfoService {
    override fun execute(): MassageInfoResDto {
        val massageCount = massageCountRepository.findMassageCountById(1L)
        val member = userUtil.fetchCurrentUser()
        return MassageInfoResDto(
            count = massageCount.count,
            massageStatus = member.massageStatus
        )
    }
}