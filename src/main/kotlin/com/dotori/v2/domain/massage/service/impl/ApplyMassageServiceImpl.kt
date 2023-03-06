package com.dotori.v2.domain.massage.service.impl

import com.dotori.v2.domain.massage.exception.MassageOverException
import com.dotori.v2.domain.massage.service.ApplyMassageService
import com.dotori.v2.domain.massage.util.FindMassageCountUtil
import com.dotori.v2.domain.massage.util.MassageCheckUtil
import com.dotori.v2.domain.massage.util.SaveMassageUtil
import com.dotori.v2.domain.massage.util.ValidDayOfWeekAndHourMassageUtil
import com.dotori.v2.domain.member.enums.MassageStatus
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class ApplyMassageServiceImpl(
    private val userUtil: UserUtil,
    private val validDayOfWeekAndHourMassageUtil: ValidDayOfWeekAndHourMassageUtil,
    private val findMassageCountUtil: FindMassageCountUtil,
    private val saveMassageUtil: SaveMassageUtil,
    private val massageCheckUtil: MassageCheckUtil,
) : ApplyMassageService {
    override fun execute() {
        validDayOfWeekAndHourMassageUtil.validateApply()
        val member = userUtil.fetchCurrentUser()
        val selfStudyCount = findMassageCountUtil.findMassageCount()
        if (selfStudyCount.count >= 50)
            throw MassageOverException()
        massageCheckUtil.isMassageStatusCan(member)
        member.updateMassageStatus(MassageStatus.APPLIED)
        selfStudyCount.addCount()
        saveMassageUtil.save(member)
    }
}