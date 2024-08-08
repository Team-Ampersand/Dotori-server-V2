package com.dotori.v2.domain.selfstudy.service.impl

import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.selfstudy.domain.entity.SelfStudyCount
import com.dotori.v2.domain.selfstudy.domain.repository.SelfStudyCountRepository
import com.dotori.v2.domain.selfstudy.presentation.dto.res.SelfStudyInfoResDto
import com.dotori.v2.domain.selfstudy.service.GetSelfStudyInfoService
import com.dotori.v2.domain.selfstudy.util.ValidDayOfWeekAndHourUtil
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetSelfStudyInfoServiceImpl(
    private val selfStudyCountRepository: SelfStudyCountRepository,
    private val validDayOfWeekAndHourUtil: ValidDayOfWeekAndHourUtil,
    private val userUtil: UserUtil
) : GetSelfStudyInfoService {

    override fun execute(): SelfStudyInfoResDto {
        val selfStudyCount = selfStudyCountRepository.findSelfStudyCountById(1)
        val member = userUtil.fetchCurrentUser()

        if (member.selfStudyStatus == SelfStudyStatus.CAN && validSelfStudyApplyCant(selfStudyCount))
            return SelfStudyInfoResDto(
                count = selfStudyCount.count,
                limit = selfStudyCount.limit,
                selfStudyStatus = SelfStudyStatus.CANT
            )

        return SelfStudyInfoResDto(
            count = selfStudyCount.count,
            limit = selfStudyCount.limit,
            selfStudyStatus = member.selfStudyStatus
        )
    }

    private fun validSelfStudyApplyCant(selfStudyCount: SelfStudyCount): Boolean {
        if (!validDayOfWeekAndHourUtil.isApplyValid() || selfStudyCount.count >= selfStudyCount.limit) {
            return true
        }
        return false
    }
}