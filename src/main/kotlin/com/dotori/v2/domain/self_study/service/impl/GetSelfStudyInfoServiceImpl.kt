package com.dotori.v2.domain.self_study.service.impl

import com.dotori.v2.domain.self_study.domain.repository.SelfStudyCountRepository
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyInfoResDto
import com.dotori.v2.domain.self_study.service.GetSelfStudyInfoService
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class GetSelfStudyInfoServiceImpl(
    private val selfStudyCountRepository: SelfStudyCountRepository,
    private val userUtil: UserUtil
) : GetSelfStudyInfoService {
    override fun execute(): SelfStudyInfoResDto {
        val selfStudyCount = selfStudyCountRepository.findSelfStudyCountById(1)
        val member = userUtil.fetchCurrentUser()
        return SelfStudyInfoResDto(
            count = selfStudyCount.count,
            selfStudyStatus = member.selfStudyStatus
        )
    }
}