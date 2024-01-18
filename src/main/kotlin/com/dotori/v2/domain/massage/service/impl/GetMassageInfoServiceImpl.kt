package com.dotori.v2.domain.massage.service.impl

import com.dotori.v2.domain.massage.domain.entity.MassageCount
import com.dotori.v2.domain.massage.domain.repository.MassageCountRepository
import com.dotori.v2.domain.massage.exception.MassageOverException
import com.dotori.v2.domain.massage.presentation.dto.res.MassageInfoResDto
import com.dotori.v2.domain.massage.service.GetMassageInfoService
import com.dotori.v2.domain.massage.util.ValidDayOfWeekAndHourMassageUtil
import com.dotori.v2.domain.member.domain.entity.Member
import com.dotori.v2.domain.member.enums.MassageStatus
import com.dotori.v2.global.error.exception.BasicException
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class GetMassageInfoServiceImpl(
    private val massageCountRepository: MassageCountRepository,
    private val validDayOfWeekAndHourMassageUtil: ValidDayOfWeekAndHourMassageUtil,
    private val userUtil: UserUtil,
) : GetMassageInfoService {
    override fun execute(): MassageInfoResDto {
        val massageCount = massageCountRepository.findMassageCountById(1L)
        val member = userUtil.fetchCurrentUser()

        if (isMassageStatusCan(member) && validMassageApplyCant(massageCount))
            return MassageInfoResDto(
                count = massageCount.count,
                massageStatus = MassageStatus.CANT,
                limit = massageCount.limit
            )

        return MassageInfoResDto(
            count = massageCount.count,
            massageStatus = member.massageStatus,
            limit = massageCount.limit
        )
    }

    private fun isMassageStatusCan(member: Member) = member.massageStatus == MassageStatus.CAN

    private fun validMassageApplyCant(massageCount: MassageCount): Boolean {
        try {
            validDayOfWeekAndHourMassageUtil.validateApply()
            if (massageCount.count >= massageCount.limit)
                throw MassageOverException()
        } catch (ex: BasicException) {
            return true
        }
        return false
    }
}