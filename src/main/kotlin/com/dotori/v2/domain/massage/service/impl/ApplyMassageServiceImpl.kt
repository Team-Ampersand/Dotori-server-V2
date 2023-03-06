package com.dotori.v2.domain.massage.service.impl

import com.dotori.v2.domain.massage.service.ApplyMassageService
import com.dotori.v2.domain.massage.util.FindMassageCountUtil
import com.dotori.v2.domain.massage.util.MassageCheckUtil
import com.dotori.v2.domain.massage.util.SaveMassageUtil
import com.dotori.v2.domain.massage.util.ValidDayOfWeekAndHourMassageUtil
import com.dotori.v2.domain.member.enums.MassageStatus
import com.dotori.v2.domain.self_study.excetpion.SelfStudyOverException
import com.dotori.v2.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class ApplyMassageServiceImpl(
    private val userUtil: UserUtil,
    private val validDayOfWeekAndHourUtil: ValidDayOfWeekAndHourMassageUtil,
    private val findMassageCountUtil: FindMassageCountUtil,
    private val saveMassageUtil: SaveMassageUtil,
    private val massageCheckUtil: MassageCheckUtil,
) : ApplyMassageService {
    override fun execute() {
        validDayOfWeekAndHourUtil.validateApply()
        val member = userUtil.fetchCurrentUser()
        val selfStudyCount = findMassageCountUtil.findSelfStudyCount()
        if (selfStudyCount.count >= 50)
            throw SelfStudyOverException()
        massageCheckUtil.isMassageStatusCan(member)
        member.updateMassageStatus(MassageStatus.APPLIED)
        selfStudyCount.addCount()
        saveMassageUtil.save(member)
    }
}