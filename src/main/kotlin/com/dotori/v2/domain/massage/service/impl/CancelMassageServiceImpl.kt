package com.dotori.v2.domain.massage.service.impl

import com.dotori.v2.domain.massage.domain.repository.MassageRepository
import com.dotori.v2.domain.massage.service.CancelMassageService
import com.dotori.v2.domain.massage.util.FindMassageCountUtil
import com.dotori.v2.domain.massage.util.MassageCheckUtil
import com.dotori.v2.domain.massage.util.ValidDayOfWeekAndHourMassageUtil
import com.dotori.v2.domain.member.enums.MassageStatus
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class CancelMassageServiceImpl(
    private val userUtil: UserUtil,
    private val validDayOfWeekAndHourMassageUtil: ValidDayOfWeekAndHourMassageUtil,
    private val findMassageCountUtil: FindMassageCountUtil,
    private val massageCheckUtil: MassageCheckUtil,
    private val massageRepository: MassageRepository,
) : CancelMassageService {
    override fun execute() {
        validDayOfWeekAndHourMassageUtil.validateCancel()
        val findMassageCount = findMassageCountUtil.findMassageCount()
        val member = userUtil.fetchCurrentUser()
        massageCheckUtil.isMassageStatusApplied(member)
        member.updateMassageStatus(MassageStatus.CANT)
        massageRepository.deleteByMember(member)
        findMassageCount.deductionCount()
    }
}