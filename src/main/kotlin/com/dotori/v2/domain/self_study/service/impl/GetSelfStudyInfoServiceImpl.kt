package com.dotori.v2.domain.self_study.service.impl

import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.SelfStudyStatus
import com.dotori.v2.domain.self_study.domain.entity.SelfStudyCount
import com.dotori.v2.domain.self_study.domain.repository.SelfStudyCountRepository
import com.dotori.v2.domain.self_study.exception.SelfStudyOverException
import com.dotori.v2.domain.self_study.presentation.dto.res.SelfStudyInfoResDto
import com.dotori.v2.domain.self_study.service.GetSelfStudyInfoService
import com.dotori.v2.domain.self_study.util.ValidDayOfWeekAndHourUtil
import com.dotori.v2.global.error.exception.BasicException
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class GetSelfStudyInfoServiceImpl(
    private val selfStudyCountRepository: SelfStudyCountRepository,
    private val validDayOfWeekAndHourUtil: ValidDayOfWeekAndHourUtil,
    private val userUtil: UserUtil
) : GetSelfStudyInfoService {
    override fun execute(): SelfStudyInfoResDto {
        val selfStudyCount = selfStudyCountRepository.findSelfStudyCountById(1)
        val member = userUtil.fetchCurrentUser()
        if (isSelfStudyStatusCan(member) && validSelfStudyApplyCant(selfStudyCount))
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

    private fun isSelfStudyStatusCan(member: Member) = member.selfStudyStatus == SelfStudyStatus.CAN

    private fun validSelfStudyApplyCant(selfStudyCount: SelfStudyCount): Boolean {
        try {
            validDayOfWeekAndHourUtil.validateApply()
            if (selfStudyCount.count >= selfStudyCount.limit)
                throw SelfStudyOverException()
        } catch (ex: BasicException) {
            return true
        }
        return false
    }
}